package de.byte_artist.quickmemoplus.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MemoEntity implements Parcelable{

    private long id = 0;

    private String text = null;

    private MemoGroupEntity group = null;

    private boolean deleted = false;

    private boolean closed = false;

    private String created = null;

    private String modified = null;

    public MemoEntity() {}

    private MemoEntity(Parcel in) {
        this.id = in.readLong();
        this.text = in.readString();
        long groupId = in.readLong();
        this.deleted = 0 != in.readInt();
        this.closed = 0 != in.readInt();
        this.created = in.readString();
        this.modified = in.readString();
    }

    public MemoEntity(String text) {
        this.text = text;
    }

    public MemoEntity setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getId());
        dest.writeString(this.getText());
//        dest.writeParcelable(this.group);

        if (null != this.getGroup()) {
            dest.writeLong(this.getGroup().getId());
        } else {
            dest.writeLong(0);
        }
        dest.writeInt(this.isDeleted() ? 1 : 0);
        dest.writeInt(this.isClosed() ? 1 : 0);
        dest.writeString(this.getCreated());
        dest.writeString(this.getModified());
    }

    public static final Parcelable.Creator<MemoEntity> CREATOR = new Parcelable.Creator<MemoEntity>(){
        public MemoEntity createFromParcel(Parcel in) {
            return new MemoEntity(in);
        }

        public MemoEntity[] newArray(int size) {
            return new MemoEntity[size];
        }
    };

    public long getId() {
        return this.id;
    }

    public MemoEntity setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public MemoEntity setGroup(MemoGroupEntity group) {
        this.group = group;
        return this;
    }

    public MemoGroupEntity getGroup() {
        return this.group;
    }

    public MemoEntity setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public MemoEntity setClosed(boolean closed) {
        this.closed = closed;
        return this;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public MemoEntity setCreated(String created) {
        this.created = created;
        return this;
    }

    public String getCreated() {
        return this.created;
    }

    public MemoEntity setModified(String modified) {
        this.modified = modified;
        return this;
    }

    public String getModified() {
        return this.modified;
    }
}
