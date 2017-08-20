package in.bhargavrao.stackoverflow.natty.model;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class OptedInUser {
    SOUser user;
    String tagname;
    String postType;
    int roomId;
    boolean whenInRoom;

    public SOUser getUser() {
        return user;
    }

    public void setUser(SOUser user) {
        this.user = user;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public boolean isWhenInRoom() {
        return whenInRoom;
    }

    public void setWhenInRoom(boolean whenInRoom) {
        this.whenInRoom = whenInRoom;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "OptedInUser{" +
                "user=" + user +
                ", tagname='" + tagname + '\'' +
                ", postType='" + postType + '\'' +
                ", roomId=" + roomId +
                ", whenInRoom=" + whenInRoom +
                '}';
    }
}
