package model;

import jakarta.persistence.*;

/**
 * Represents a character in the game.
 */
@Entity
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long character_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "element", nullable = false)
    private String element;

    @Column(name = "weapon_type", nullable = false)
    private String weapon_type;

    @Column(name = "rarity", nullable = false)
    private String rarity;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "photo", nullable = false)
    private String photo;

    @Column(name = "artifact_set", nullable = false)
    private String artifact_set;

    @Column(name = "artifact_sands_main_stat", nullable = false)
    private String artifact_sands_main_stat;

    @Column(name = "artifact_goblet_main_stat", nullable = false)
    private String artifact_goblet_main_stat;

    @Column(name = "artifact_circlet_main_stat", nullable = false)
    private String artifact_circlet_main_stat;

    @Column(name = "recommended_weapon", nullable = false)
    private String recommended_weapon;

    public Long getCharacterId() {
        return character_id;
    }

    public void setCharacterId(Long characterId) {
        this.character_id = characterId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getWeapon_type() {
        return weapon_type;
    }

    public void setWeapon_type(String weapon_type) {
        this.weapon_type = weapon_type;
    }


    public String getArtifact_set() {
        return artifact_set;
    }

    public void setArtifact_set(String artifact_set) {
        this.artifact_set = artifact_set;
    }

    public String getArtifact_sands_main_stat() {
        return artifact_sands_main_stat;
    }

    public void setArtifact_sands_main_stat(String artifact_sands_main_stat) {
        this.artifact_sands_main_stat = artifact_sands_main_stat;
    }

    public String getArtifact_goblet_main_stat() {
        return artifact_goblet_main_stat;
    }

    public void setArtifact_goblet_main_stat(String artifact_goblet_main_stat) {
        this.artifact_goblet_main_stat = artifact_goblet_main_stat;
    }

    public String getArtifact_circlet_main_stat() {
        return artifact_circlet_main_stat;
    }

    public void setArtifact_circlet_main_stat(String artifact_circlet_main_stat) {
        this.artifact_circlet_main_stat = artifact_circlet_main_stat;
    }

    public String getRecommended_weapon() {
        return recommended_weapon;
    }

    public void setRecommended_weapon(String recommended_weapon) {
        this.recommended_weapon = recommended_weapon;
    }
}

