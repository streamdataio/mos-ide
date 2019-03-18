package com.motwin.ide.server.connectors.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.virgo.ide.runtime.core.ServerUtils;
import org.eclipse.virgo.ide.runtime.internal.core.runtimes.VirgoRuntimeProvider;
import org.eclipse.wst.server.core.IRuntime;


public class ConnectorUtils {
	
	public static boolean hasVirgoRuntime(IProject aProject) {		
		return getFirstVirgoRuntime(aProject) != null;
	}
	
	public static IRuntime getFirstVirgoRuntime(IProject aProject) {
		IRuntime[] runtimes;
		runtimes = ServerUtils.getTargettedRuntimes(aProject);
		
		for(IRuntime candidateRuntime : runtimes) {
			if(candidateRuntime.getRuntimeType().getId().equals(VirgoRuntimeProvider.SERVER_VIRGO_BASE)) {
				return candidateRuntime;
			}
		}
		
		return null;
	}

}
