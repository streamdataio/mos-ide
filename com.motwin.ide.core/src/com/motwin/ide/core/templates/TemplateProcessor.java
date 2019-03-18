/**
 * 
 */
package com.motwin.ide.core.templates;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.PatternFilenameFilter;

/**
 * @author fbou
 * 
 */
public class TemplateProcessor {

    private final Map<String, String> context;
    private final FilenameFilter      copyOnlyFilter;

    /**
     * @param aContext
     */
    public TemplateProcessor(Map<String, String> aContext) {
        this(aContext, new PatternFilenameFilter(".*(\\.jar|\\.a|\\.png|\\.jpg|\\.jpeg|\\.gif)"));
    }

    /**
     * @param aContext
     * @param aCopyOnlyFilter
     */
    public TemplateProcessor(Map<String, String> aContext, FilenameFilter aCopyOnlyFilter) {
        context = ImmutableMap.copyOf(aContext);
        copyOnlyFilter = Preconditions.checkNotNull(aCopyOnlyFilter);
    }

    /**
     * 
     * @param aTemplateDirectory
     * @param aTargetDirectory
     * @throws IOException
     */
    public void process(File aTemplateDirectory, File aTargetDirectory) throws IOException {
        Preconditions.checkArgument(aTemplateDirectory.isDirectory());
        Preconditions.checkArgument(aTargetDirectory.isDirectory());

        processDirectory(aTemplateDirectory, aTargetDirectory);

    }

    /**
     * @param aTemplateDirectory
     * @param aTargetDirectory
     * @throws IOException
     */
    private void processDirectory(File aTemplateDirectory, File aTargetDirectory) throws IOException {
        Preconditions.checkNotNull(aTemplateDirectory);
        Preconditions.checkNotNull(aTargetDirectory);

        if (!aTargetDirectory.exists()) {
            aTargetDirectory.mkdirs();

        }

        for (File templateFile : aTemplateDirectory.listFiles()) {
            String newFileName = replaceUsingContext(templateFile.getName());
            File targetFile = new File(aTargetDirectory.getAbsolutePath() + File.separator + newFileName);

            if (templateFile.isDirectory()) {
                processDirectory(templateFile, targetFile);

            } else {
                processFile(templateFile, targetFile);

            }
        }
    }

    /**
     * @param aTemplateFile
     * @param aTargetFile
     * @throws IOException
     */
    private void processFile(File aTemplateFile, File aTargetFile) throws IOException {
        if (copyOnlyFilter.accept(aTemplateFile.getParentFile(), aTemplateFile.getName())) {
            FileUtils.copyFile(aTemplateFile, aTargetFile);
        } else {
            String stringContent = FileUtils.readFileToString(aTemplateFile);
            String newStringContent = replaceUsingContext(stringContent);
            FileUtils.writeStringToFile(aTargetFile, newStringContent);
        }
    }

    private String replaceUsingContext(String aTemplateString) {
        String newString = Preconditions.checkNotNull(aTemplateString);
        for (Entry<String, String> aContextEntry : context.entrySet()) {
            newString = newString.replace(aContextEntry.getKey(), aContextEntry.getValue());
        }
        return newString;
    }

}
