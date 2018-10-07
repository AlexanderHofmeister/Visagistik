package de.visagistikmanager.model.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.itextpdf.html2pdf.HtmlConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateUtil {

	public static void buildPdfFromTemplate(final TemplateFile template, final VelocityContext context,
			final String name) {
		final StringWriter stringWriter = new StringWriter();
		Velocity.evaluate(context, stringWriter, "PackageTemplatesVelocity", new String(template.getData()));
		try {
			HtmlConverter.convertToPdf(stringWriter.toString(), new FileOutputStream(new File(name)));
		} catch (final IOException e) {
			log.error("Could not save template " + template.getFileName() + " to location " + name);
			e.printStackTrace();
		}
	}
}
