import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Answer {
	private int error;
	private int server_error;
    private int lengthAnswer;
    byte [] answer;
	
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
    public int getServer_error() {
        return server_error;
    }
    public void setServer_error(int server_error) {
        this.server_error = server_error;
    }
    public int getLengthAnswer() {
        return lengthAnswer;
    }
    public void setLengthAnswer(int lengthAnswer) {
        this.lengthAnswer = lengthAnswer;
    }
	public byte [] getAnswer() {
		return answer;
	}
	public void setAnswer(byte [] answer) {
		this.answer = answer;
	}
	public byte [] toByteStruct() {
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeInt(this.error);
			dtParams.writeInt(this.server_error);
            dtParams.writeInt(this.lengthAnswer);
			if(answer == null) {
				dtParams.writeUTF("");
			} else {
				dtParams.write(answer);
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		byte[] byParams = baParams.toByteArray();
		return byParams;
	}
	
	public Answer(int error, int server_error, byte [] answer) {
		super();
		this.error = error;
		this.server_error = server_error;
		this.answer = answer;
	}
	
	public Answer() {
		super();
		this.error = 1;
		this.server_error = 0;
		this.answer = null;
	}
	
	
}
