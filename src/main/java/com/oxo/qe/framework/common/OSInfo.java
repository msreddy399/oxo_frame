package com.oxo.qe.framework.common;


import java.io.IOException;
import java.util.Locale;

public class OSInfo {


	public enum OS {
		WINDOWS,
		UNIX,
		POSIX_UNIX,
		MAC,
		OTHER;

		private String version;
		private String arch;

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getArch() {
			return arch;
		}

		public void setArch(String arch) {
			this.arch = arch;
		}
		
	}

	private static OS os = OS.OTHER;

	static {
		try {
			String osName = System.getProperty("os.name");
			//System.getProperty("os.name", "generic");
			if (osName == null) {
				throw new IOException("os.name not found");
			}
			osName = osName.toLowerCase(Locale.ENGLISH);
			if (osName.contains("win")) {
				os = OS.WINDOWS;
			} else if (osName.contains("linux")
					|| osName.contains("mpe/ix")
					|| osName.contains("freebsd")
					|| osName.contains("irix")
					|| osName.contains("digital unix")
					|| osName.contains("unix")) {
				os = OS.UNIX;
			} else if (osName.contains("mac")) {
				os = OS.MAC;
			} else if (osName.contains("sun")
					|| osName.contains("sunos")
					|| osName.contains("solaris")) {
				os = OS.POSIX_UNIX;
			} else if (osName.contains("hp-ux") 
					|| osName.contains("aix")) {
				os = OS.POSIX_UNIX;
			} else {
				os = OS.OTHER;
			}

		} catch (Exception ex) {
			os = OS.OTHER;
		} finally {
			os.setVersion(System.getProperty("os.version")); // The key for getting operating system version
			os.setArch(System.getProperty("os.arch"));// The key for getting operating system architecture
		}
	}

	public static void setOSName(String osName){
		try {
			osName = osName.toLowerCase(Locale.ENGLISH);
			if (osName.contains("win")) {
				os = OS.WINDOWS;
			} else if (osName.contains("linux")
					|| osName.contains("mpe/ix")
					|| osName.contains("freebsd")
					|| osName.contains("irix")
					|| osName.contains("digital unix")
					|| osName.contains("unix")) {
				os = OS.UNIX;
			} else if (osName.contains("mac")) {
				System.out.println("************");
				os = OS.MAC;
			} else if (osName.contains("sun os")
					|| osName.contains("sunos")
					|| osName.contains("solaris")) {
				os = OS.POSIX_UNIX;
			} else if (osName.contains("hp-ux") 
					|| osName.contains("aix")) {
				os = OS.POSIX_UNIX;
			} else {
				os = OS.OTHER;
			}

		} catch (Exception ex) {
			os = OS.OTHER;
		}
		
	}
	public static OS getOs() {
		return os;
	}
}
