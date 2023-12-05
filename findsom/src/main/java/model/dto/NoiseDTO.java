package model.dto;

public class NoiseDTO {
	private String roomInfo;
	private int count;
	
	public NoiseDTO(String roomInfo, int count) {
		this.roomInfo = roomInfo;
		this.count = count;
	}

	public String getRoomInfo() {
		return roomInfo;
	}

	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
