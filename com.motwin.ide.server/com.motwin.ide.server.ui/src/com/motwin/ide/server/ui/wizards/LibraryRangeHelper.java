/**
 * 
 */
package com.motwin.ide.server.ui.wizards;

import org.eclipse.virgo.ide.facet.core.FacetCorePlugin;
import org.eclipse.virgo.ide.runtime.core.ServerCorePlugin;
import org.eclipse.virgo.kernel.repository.BundleRepository;
import org.eclipse.virgo.kernel.repository.LibraryDefinition;
import org.eclipse.wst.server.core.IRuntime;
import org.eclipse.wst.server.core.ServerUtil;
import org.osgi.framework.Version;

import com.google.common.base.Preconditions;

/**
 * @author ctranxuan
 * 
 */
final class LibraryRangeHelper {
    static final String  SPRING_LIB_SYMBOLIC_NAME          = "org.springframework.spring";
    static final String  MOTWIN_SDK_LIB_SYMBOLIC_NAME      = "com.motwin.sdk";
    static final String  MOTWIN_SDK_TEST_LIB_SYMBOLIC_NAME = "com.motwin.sdk.test";

    static final String  DEFAULT_SPRING_RANGE              = "[3.0,4.0)";
    static final String  DEFAULT_MOTWIN_RANGE              = "[3.1,4.0)";
    static final String  DEFAULT_MOTWIN_TEST_RANGE         = "[3.1,4.0)";

    private final String runtimeName;

    /**
     * @param aRuntimeName
     */
    public LibraryRangeHelper(final String aRuntimeName) {
        runtimeName = aRuntimeName;
    }

    public String getSpringLibRange() {
        String range;
        range = getLibraryRange(SPRING_LIB_SYMBOLIC_NAME, new Version(0, 1, 0), DEFAULT_SPRING_RANGE);

        return range;
    }

    public String getSDKLibRange() {
        String range;
        range = getLibraryRange(MOTWIN_SDK_LIB_SYMBOLIC_NAME, new Version(0, 1, 0), DEFAULT_MOTWIN_RANGE);

        return range;
    }

    public String getSDKTestLibRange() {
        String range;
        range = getLibraryRange(MOTWIN_SDK_TEST_LIB_SYMBOLIC_NAME, new Version(0, 1, 0), DEFAULT_MOTWIN_TEST_RANGE);

        return range;
    }

    private String getLibraryRange(final String aSymbolicName, final Version anIncrementVersion,
                                   final String aDefaultRange) {
        Preconditions.checkNotNull(aSymbolicName, "aSymbolicName cannot be null");
        Preconditions.checkNotNull(aDefaultRange, "aDefaultRange cannot be null");

        String libRange;
        libRange = aDefaultRange;

        LibraryDefinition libraryDefinition;
        libraryDefinition = findLatestLibrary(aSymbolicName);

        if (libraryDefinition != null) {
            Version libVersion;
            libVersion = libraryDefinition.getVersion();

            // @formatter:off
                // we strip the qualifier
                Version version;
                version = new Version(
                        libVersion.getMajor(),
                        libVersion.getMinor(),
                        0
                        );

                Version maxVersion;
                maxVersion = new Version(
                        version.getMajor() + anIncrementVersion.getMajor(), 
                        version.getMinor() + anIncrementVersion.getMinor(), 
                        0);
                // @formatter:on

            libRange = "[" + version + "," + maxVersion + ")";
        }

        return libRange;
    }

    private LibraryDefinition findLatestLibrary(final String aLibSymbolicName) {
        Preconditions.checkNotNull(aLibSymbolicName, "aLibSymbolicName cannot be null");

        LibraryDefinition libraryDefinition;
        libraryDefinition = null;

        IRuntime runtime;
        runtime = getRuntime();

        if (runtime != null) {

            BundleRepository bundleRepository;
            bundleRepository = ServerCorePlugin.getArtefactRepositoryManager().getBundleRepository(runtime);

            Iterable<? extends LibraryDefinition> libraries;
            libraries = bundleRepository.getLibraries();

            for (LibraryDefinition libDefinition : libraries) {

                String symbolicName;
                symbolicName = libDefinition.getSymbolicName();

                if (aLibSymbolicName.equals(symbolicName)) {
                    libraryDefinition = latestLibrary(libraryDefinition, libDefinition);
                }
            }
        }

        return libraryDefinition;
    }

    /**
     * @return
     */
    private IRuntime getRuntime() {
        IRuntime[] runtimes;
        runtimes = ServerUtil.getRuntimes(FacetCorePlugin.BUNDLE_FACET_ID, null);

        IRuntime runtime;
        runtime = null;

        for (int i = 0; runtime == null && i < runtimes.length; i++) {

            if (runtimeName.equals(runtimes[i].getName())) {
                runtime = runtimes[i];
            }
        }
        return runtime;
    }

    /**
     * Returns the latest library among the two given as a parameter or
     * <code>null</code> if both of the parameters are <code>null</code>.
     * 
     * @param aLibDefinition1
     *            the lib definition1
     * @param aLibDefinition2
     *            the lib definition2
     * @return the library definition
     */
    private LibraryDefinition latestLibrary(final LibraryDefinition aLibDefinition1,
                                            final LibraryDefinition aLibDefinition2) {
        LibraryDefinition libraryDefinition;
        libraryDefinition = null;

        if (aLibDefinition1 == null) {
            libraryDefinition = aLibDefinition2;

        } else if (aLibDefinition2 == null) {
            libraryDefinition = aLibDefinition1;

        } else if (aLibDefinition1.getVersion().compareTo(aLibDefinition2.getVersion()) <= 0) {
            libraryDefinition = aLibDefinition2;

        } else {
            libraryDefinition = aLibDefinition1;
        }

        return libraryDefinition;
    }

}
