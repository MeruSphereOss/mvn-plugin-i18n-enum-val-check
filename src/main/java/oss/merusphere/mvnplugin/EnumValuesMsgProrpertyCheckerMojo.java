package oss.merusphere.mvnplugin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.reflections.Reflections;

/**
 * Checks that whether Enum Field Types in model classes contain Enumerated
 * Annotation or not and also checks that for enum fields containing enumerated
 * annotation, whether enumerated annotation contains Enum String Value or not
 */
@Mojo(name = "i18n-enum-val-check", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.TEST)
public class EnumValuesMsgProrpertyCheckerMojo extends AbstractMojo {
	public static Set<String> msgPropertyCodeSet = new HashSet<>();

	/**
	 * Scan the classes from the Package name from the configuration parameter named
	 * pkg
	 */
	@Parameter(property = "pkg")
	String pkg;

	/**
	 * Path of message properties
	 */
	@Parameter(property = "msgPropertiesPath")
	String msgPropertiesPath;

	/**
	 * Gives access to the Maven project information.
	 */
	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	MavenProject project;

	/**
	 * Checks the number of methods not having Security Annotations
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			if (pkg == null || pkg.trim().length() <= 0) {
				new MojoExecutionException("Package name can't be null for the project : " + project.getName());
			}

			if (getLog().isInfoEnabled()) {
				getLog().info(new StringBuffer("Using Package " + pkg));
			}

			if (msgPropertiesPath == null || msgPropertiesPath.trim().isEmpty()) {
				new MojoExecutionException(
						"Msg properties path cannot be empty for the project : " + project.getName());
			}

			loadMessageProperties(msgPropertiesPath);

			// Use Reflections to get all classes in the package
			Reflections reflections = new Reflections(pkg);
			Set<Class<? extends Enum>> allEnumClasses = reflections.getSubTypesOf(Enum.class);

			if (allEnumClasses == null || allEnumClasses.isEmpty()) {
				if (getLog().isInfoEnabled()) {
					getLog().info("No Enum classes Found");
				}
				return;
			}

			List<String> enumValAbsenceList = new ArrayList<>();
			// Filter out only enum classes
			for (Class<?> clazz : allEnumClasses) {
				Object[] enumConstants = clazz.getEnumConstants();
				if (enumConstants != null) {
					for (Object enumConstant : enumConstants) {
						String msgCode = clazz.getSimpleName() + "." + enumConstant;
						if (!msgPropertyCodeSet.contains(msgCode)) {
							enumValAbsenceList.add(msgCode);
						}
					}
				}	
			}
			
			if (enumValAbsenceList != null && !enumValAbsenceList.isEmpty()) {
				getLog().error("The following enum message codes does not exists in message properties");
				for (String error : enumValAbsenceList) {
					getLog().error(error);
				}
			}
			
			if (!enumValAbsenceList.isEmpty() || !enumValAbsenceList.isEmpty()) {
				new MojoExecutionException("Found Enum Message Code Errors");
			}
			
		} catch (Exception e) {
			getLog().error(e.getMessage(), e);
			new MojoExecutionException(e.getMessage(), e);
		}
	}

	public static void loadMessageProperties(String msgPropertiesPath) {
		// Method 1: Using BufferedReader
		try (BufferedReader br = new BufferedReader(new FileReader(msgPropertiesPath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String split[] = line.split("=");
				if (split != null && split.length > 0) {
					msgPropertyCodeSet.add(split[0].trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
