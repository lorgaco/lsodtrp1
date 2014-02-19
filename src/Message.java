
public class Message {
	private int iTypeMessage;
	private int iIdMessage; 	// sequence ID
	private int iIdMethod;
	private int iLengthArgs;
	private byte [] byArguments;
	
	public int getiTypeMessage() {
		return iTypeMessage;
	}
	public void setiTypeMessage(int iTypeMessage) {
		this.iTypeMessage = iTypeMessage;
	}
	public int getiIdMessage() {
		return iIdMessage;
	}
	public void setiIdMessage(int iIdMessage) {
		this.iIdMessage = iIdMessage;
	}
	public int getiIdMethod() {
		return iIdMethod;
	}
	public void setiIdMethod(int iIdMethod) {
		this.iIdMethod = iIdMethod;
	}
	public int getiLengthArgs() {
		return iLengthArgs;
	}
	public void setiLengthArgs(int iLengthArgs) {
		this.iLengthArgs = iLengthArgs;
	}
	public byte[] getbyArguments() {
		return byArguments;
	}
	public void setbyArguments(byte[] byArguments) {
		this.byArguments = byArguments;
	}
	
	
}
