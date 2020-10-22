package HttpServer;

public class HttpEnum {
	public enum eHttpPath {
		COM1("/iiwa_com1"), COM2("/iiwa_com2"), COM3("/iiwa_com3"), COM4("/iiwa_com4");

		private String path;

		private eHttpPath(String path) {
			this.path = path;
		}

		public String getValue() {
			return this.path;
		}

//		public static eHttpPath valueOf(String value) {
//	        for (eHttpPath path : eHttpPath.values()) {
//	            if (path.getValue().contentEquals(value)){ 
//	            	return path;
//	            }
//	        }
//			return null;   
//	    }
	};
}
