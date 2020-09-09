package Camera;

public class CameraCommands {
	
	
	public enum eCamCommand {
		SetOnline("so1"),
		SetOffline("so0"),
		Trigger("sw8"),
		GetError("gvb17"),
		GetResultX("gvb14"),
		GetResultY("gvb15"),
		GetResultA("gvb16"),
		ChangeProgram("sj");
		
		
		private  String value;
		private eCamCommand(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
		
	};

}

