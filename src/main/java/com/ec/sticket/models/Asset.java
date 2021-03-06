package com.ec.sticket.models;

import com.ec.sticket.models.mapping.SticonAsset;
import com.ec.sticket.models.mapping.UserAssetPurchase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ApiModel(description = "asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AssetGenerator")
    @ApiModelProperty(notes = "Id for Asset")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "asset")
    private List<UserAssetPurchase> userAssetPurchases = new ArrayList<>();

    @OneToMany(mappedBy = "asset")
    private List<SticonAsset> sticonAssets = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "asset_theme",
            joinColumns = @JoinColumn(name = "asset_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id",
                    referencedColumnName = "id")
    )
    private List<Theme> themes = new ArrayList<>();

    @ApiModelProperty(notes = "name for Asset", example = "에셋이름!!")
    private String name;
    private String imgUrl;
    private LocalDateTime createdTime;
    @ApiModelProperty(notes = "price for Asset", example = "30")
    private int price;
    private String description;
    private int likeCnt;
    private int purchaseCnt;
    @Enumerated(value = EnumType.STRING)
    private Landmark landmark;

    public Asset() {
        createdTime = LocalDateTime.now();
        likeCnt = 0;
        purchaseCnt = 0;
    }

    public Asset(User author, Landmark landmark, List<Theme> themes, String name, String imgUrl, int price
            , String description) {
        this.author = author;
        this.landmark = landmark;
        this.themes = themes;
        this.name = name;
        this.imgUrl = imgUrl;
        this.createdTime = LocalDateTime.now();
        this.price = price;
        this.description = description;
        this.likeCnt = 0;
        this.purchaseCnt = 0;
    }

    public void setAuthor(User author) {
        if (this.author == null) {
            author.getSellingAssets().add(this);
            this.author = author;
        } else {
            throw new RuntimeException("Cannot modify author");
        }
    }

    public enum Landmark {
        EYE_LEFT, EYE_RIGHT, NOSE, MOUTH_LEFT, MOUTH_RIGHT, MOUTH_BOTTOM, CHEEK_LEFT, CHEEK_RIGHT
    }
}
