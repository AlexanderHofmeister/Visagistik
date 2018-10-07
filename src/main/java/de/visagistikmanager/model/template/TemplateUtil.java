package de.visagistikmanager.model.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.itextpdf.html2pdf.HtmlConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateUtil {

	public static String BASE_DIRECTORY = "Archiv/";

	public static String BASE_DIRECTORY_BILLS = "Rechnungen/";

	public static String buildBillPathName(final LocalDate date) {
		return buildPathName(BASE_DIRECTORY_BILLS, date);
	}

	private static String buildPathName(final String templateSubDirectory, final LocalDate date) {
		return BASE_DIRECTORY + templateSubDirectory + buildPathFromDate(date);
	}

	private static String buildPathFromDate(final LocalDate date) {
		return date.getYear() + "/" + date.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMANY) + "/";
	}

	public static void buildBill(final TemplateFile template, final VelocityContext context, final String filename,
			final LocalDate date) {
		buildPdfFromTemplate(template, context, buildBillPathName(date) + filename);
	}

	private static void buildPdfFromTemplate(final TemplateFile template, final VelocityContext context,
			final String filename) {
		final StringWriter stringWriter = new StringWriter();
		Velocity.evaluate(context, stringWriter, "PackageTemplatesVelocity", new String(template.getData()));
		final File file = new File(filename);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
			HtmlConverter.convertToPdf(stringWriter.toString(), new FileOutputStream(file));
		} catch (final IOException e) {
			log.error("Could not save template " + template.getFileName() + " to location " + filename);
			e.printStackTrace();
		}
	}

}
