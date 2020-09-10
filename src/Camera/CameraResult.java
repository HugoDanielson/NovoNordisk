package Camera;

public class CameraResult {

	private Double resX;
	private Double resY;
	private Double resA;
	private String status;
	private Double error;
	private Integer ActProgramNr;

	public CameraResult() {

		resX = new Double(-99);
		resY = new Double(-99);
		resA = new Double(-99);
		status = new String("NA");
		error = new Double(0);
		ActProgramNr = new Integer(-99);
	}

	public CameraResult(Double resX, Double resY, String status, Double error, Integer programNr) {

		this.resX = resX;
		this.resY = resY;
		this.status = status;
		this.error = error;
		this.ActProgramNr = programNr;
	}

	public Double getResX() {
		return resX;
	}

	public void setResX(Double resX) {
		this.resX = resX;
	}

	public Double getResY() {
		return resY;
	}

	public void setResY(Double resY) {
		this.resY = resY;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getError() {
		return error;
	}

	public void setError(Double error) {
		this.error = error;
	}

	public Integer getProgramNr() {
		return ActProgramNr;
	}

	public void setProgramNr(Integer programNr) {
		this.ActProgramNr = programNr;
	}

	public void setResA(Double resA) {
		this.resA = resA;

	}

	public Double getResA() {
		return resA;
	}
	@Override
	public String toString(){
		return "CameraResult: X="+resX+", Y="+resY+", A="+resA+", Err="+error+", ActProgramNr="+ActProgramNr;
		
	}

}
