package de.visagistikmanager.view.components;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.InputEvent;
import javafx.util.StringConverter;

public class TimeSpinner extends Spinner<LocalTime> {

	// Mode represents the unit that is currently being edited.
	// For convenience expose methods for incrementing and decrementing that
	// unit, and for selecting the appropriate portion in a spinner's editor
	enum Mode {

		HOURS {
			@Override
			LocalTime increment(final LocalTime time, final int steps) {
				return time.plusHours(steps);
			}

			@Override
			void select(final TimeSpinner spinner) {
				final int index = spinner.getEditor().getText().indexOf(':');
				spinner.getEditor().selectRange(0, index);
			}
		},
		MINUTES {
			@Override
			LocalTime increment(final LocalTime time, final int steps) {
				return time.plusMinutes(steps);
			}

			@Override
			void select(final TimeSpinner spinner) {
				final int hrIndex = spinner.getEditor().getText().indexOf(':');
				final int minIndex = spinner.getEditor().getText().indexOf(':', hrIndex + 1);
				spinner.getEditor().selectRange(hrIndex + 1, minIndex);
			}
		},
		SECONDS {
			@Override
			LocalTime increment(final LocalTime time, final int steps) {
				return time.plusSeconds(steps);
			}

			@Override
			void select(final TimeSpinner spinner) {
				final int index = spinner.getEditor().getText().lastIndexOf(':');
				spinner.getEditor().selectRange(index + 1, spinner.getEditor().getText().length());
			}
		};
		abstract LocalTime increment(LocalTime time, int steps);

		abstract void select(TimeSpinner spinner);

		LocalTime decrement(final LocalTime time, final int steps) {
			return increment(time, -steps);
		}
	}

	// Property containing the current editing mode:

	private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.HOURS);

	public ObjectProperty<Mode> modeProperty() {
		return this.mode;
	}

	public final Mode getMode() {
		return modeProperty().get();
	}

	public final void setMode(final Mode mode) {
		modeProperty().set(mode);
	}

	public TimeSpinner(final LocalTime time) {
		setEditable(true);

		// Create a StringConverter for converting between the text in the
		// editor and the actual value:

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		final StringConverter<LocalTime> localTimeConverter = new StringConverter<LocalTime>() {

			@Override
			public String toString(final LocalTime time) {
				return formatter.format(time);
			}

			@Override
			public LocalTime fromString(final String string) {
				final String[] tokens = string.split(":");
				final int hours = getIntField(tokens, 0);
				final int minutes = getIntField(tokens, 1);
				final int seconds = getIntField(tokens, 2);
				final int totalSeconds = (hours * 60 + minutes) * 60 + seconds;
				return LocalTime.of(totalSeconds / 3600 % 24, totalSeconds / 60 % 60, seconds % 60);
			}

			private int getIntField(final String[] tokens, final int index) {
				if (tokens.length <= index || tokens[index].isEmpty()) {
					return 0;
				}
				return Integer.parseInt(tokens[index]);
			}

		};

		// The textFormatter both manages the text <-> LocalTime conversion,
		// and vetoes any edits that are not valid. We just make sure we have
		// two colons and only digits in between:

		final TextFormatter<LocalTime> textFormatter = new TextFormatter<LocalTime>(localTimeConverter, LocalTime.now(),
				c -> {
					final String newText = c.getControlNewText();
					if (newText.matches("[0-9]{0,2}:[0-9]{0,2}:[0-9]{0,2}")) {
						return c;
					}
					return null;
				});

		// The spinner value factory defines increment and decrement by
		// delegating to the current editing mode:

		final SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
			{
				setConverter(localTimeConverter);
				setValue(time);
			}

			@Override
			public void decrement(final int steps) {
				setValue(TimeSpinner.this.mode.get().decrement(getValue(), steps));
				TimeSpinner.this.mode.get().select(TimeSpinner.this);
			}

			@Override
			public void increment(final int steps) {
				setValue(TimeSpinner.this.mode.get().increment(getValue(), steps));
				TimeSpinner.this.mode.get().select(TimeSpinner.this);
			}

		};

		this.setValueFactory(valueFactory);
		this.getEditor().setTextFormatter(textFormatter);

		// Update the mode when the user interacts with the editor.
		// This is a bit of a hack, e.g. calling spinner.getEditor().positionCaret()
		// could result in incorrect state. Directly observing the caretPostion
		// didn't work well though; getting that to work properly might be
		// a better approach in the long run.
		this.getEditor().addEventHandler(InputEvent.ANY, e -> {
			final int caretPos = this.getEditor().getCaretPosition();
			final int hrIndex = this.getEditor().getText().indexOf(':');
			final int minIndex = this.getEditor().getText().indexOf(':', hrIndex + 1);
			if (caretPos <= hrIndex) {
				this.mode.set(Mode.HOURS);
			} else if (caretPos <= minIndex) {
				this.mode.set(Mode.MINUTES);
			} else {
				this.mode.set(Mode.SECONDS);
			}
		});

		// When the mode changes, select the new portion:
		this.mode.addListener((obs, oldMode, newMode) -> newMode.select(this));

	}

	public TimeSpinner() {
		this(LocalTime.now());
	}
}