package es.udc.fi.dc.fd.model.entities;

import java.util.List;

/**
 * The group.
 *
 * @author jorge
 */
 public class Group {

    /** The group id. */
    private Long id;

    /** The group owner. */
    private Long owner;

    /** The group name. */
    private String name;

    /** The group members. */
    private List<Users> members;

    /**
     * Instantiates a new group.
     */
    public Group() {
    }

    /**
     * Instantiates a new group.
     *
     * @param owner   the owner
     * @param name    the name
     * @param members the members
     */
    public Group(final Long owner, final String name, final List<Users> members) {
        this.owner = owner;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Gets the owner.
     *
     * @return the owner
     */
    public Long getOwner() {
        return owner;
    }

    /**
     * Sets the owner.
     *
     * @param owner the new owner
     */
    public void setOwner(final Long owner) {
        this.owner = owner;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the members.
     *
     * @return the members
     */
    public List<Users> getMembers() {
        return members;
    }

    /**
     * Sets the members.
     *
     * @param members the new members
     */
    public void setMembers(final List<Users> members) {
        this.members = members;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Group [id=" + id + ", owner=" + owner + ", name=" + name + ", members=" + members
                + "]";
    }

}
