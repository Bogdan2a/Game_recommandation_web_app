package model;

import jakarta.persistence.*;


/**
 * Entity class representing a saved team in the system.
 */
@Entity
@Table(name = "saved_teams")
public class SavedTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long team_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "maindpscharacter_id")
    private Long maindpscharacter_id;

    @Column(name = "subcharacter1")
    private Long subcharacter1;

    @Column(name = "subcharacter2")
    private Long subcharacter2;

    @Column(name = "subcharacter3")
    private Long subcharacter3;

    public Long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Long team_id) {
        this.team_id = team_id;
    }

    public Long getSubcharacter3() {
        return subcharacter3;
    }

    public void setSubcharacter3(Long subcharacter3) {
        this.subcharacter3 = subcharacter3;
    }

    public Long getSubcharacter2() {
        return subcharacter2;
    }

    public void setSubcharacter2(Long subcharacter2) {
        this.subcharacter2 = subcharacter2;
    }

    public Long getSubcharacter1() {
        return subcharacter1;
    }

    public void setSubcharacter1(Long subcharacter1) {
        this.subcharacter1 = subcharacter1;
    }

    public Long getMaindpscharacter_id() {
        return maindpscharacter_id;
    }

    public void setMaindpscharacter_id(Long maindpscharacter_id) {
        this.maindpscharacter_id = maindpscharacter_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}