import java.io.*;


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
                dtParams.writeInt(0);
			} else {
                dtParams.writeInt(this.answer.length);
				dtParams.write(answer);
			}
		} catch (IOException e) {
			System.err.println("Answer ERROR: " + e.getMessage());
		}
		byte[] byParams = baParams.toByteArray();
		return byParams;
	}

    public Answer(byte [] byCreation){
        ByteArrayInputStream baCreation = new ByteArrayInputStream(byCreation);
        DataInputStream dtCreation = new DataInputStream(baCreation);
        try {
            this.error = dtCreation.readInt();
            this.server_error = dtCreation.readInt();
            this.lengthAnswer = dtCreation.readInt();
            int bytes = dtCreation.readInt();
            this.answer = new byte[Data.MAX_ARGUMENTS_SIZE];
            dtCreation.read(this.answer, 0, bytes);
        } catch (Exception e) {
            System.err.println("Answer ERROR: " + e.getMessage());
        }
    }
	
	public Answer() {
		super();
		this.error = 1;
		this.server_error = 0;
		this.answer = null;
	}
	
	
}
