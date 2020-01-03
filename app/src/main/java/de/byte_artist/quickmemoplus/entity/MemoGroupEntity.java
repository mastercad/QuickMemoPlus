package de.byte_artist.quickmemoplus.entity;

public class MemoGroupEntity {

    private long id = 0;

    private String name = null;

    private long order = 0;

    private String icon = "";

    private String color = "";

    private boolean deleted = false;

    private boolean closed = false;

    private String created = null;

    private String modified = null;

    public MemoGroupEntity() {}

    public MemoGroupEntity(String name) {
        this.setName(name);
    }

    public MemoGroupEntity(String name, String icon) {
        this.setName(name);
        this.setIcon(icon);
    }

    public MemoGroupEntity setId(long id) {
        this.id = id;
        return this;
    }

    public long getId() {
        return this.id;
    }

    public MemoGroupEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public MemoGroupEntity setOrder(long order) {
        this.order = order;
        return this;
    }

    public long getOrder() {
        return this.order;
    }

    public MemoGroupEntity setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getIcon() {
        return this.icon;
    }

    public MemoGroupEntity setColor(String color) {
        this.color = color;
        return this;
    }

    public String getColor() {
        return this.color;
    }

    public MemoGroupEntity setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public MemoGroupEntity setClosed(boolean closed) {
        this.closed = closed;
        return this;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public MemoGroupEntity setCreated(String created) {
        this.created = created;
        return this;
    }

    public String getCreated() {
        return this.created;
    }

    public MemoGroupEntity setModified(String modified) {
        this.modified = modified;
        return this;
    }

    public String getModified() {
        return this.modified;
    }
}
