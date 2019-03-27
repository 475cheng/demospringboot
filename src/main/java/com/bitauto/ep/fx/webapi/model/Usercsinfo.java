package com.bitauto.ep.fx.webapi.model;

import java.util.Date;
import javax.persistence.*;

public class Usercsinfo {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UserId")
    private Long userid;

    @Column(name = "CsId")
    private Integer csid;

    @Column(name = "IsActive")
    private Integer isactive;

    @Column(name = "CreateTime")
    private Date createtime;

    @Column(name = "UpdateTime")
    private Date updatetime;

    /**
     * @return Id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return UserId
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * @return CsId
     */
    public Integer getCsid() {
        return csid;
    }

    /**
     * @param csid
     */
    public void setCsid(Integer csid) {
        this.csid = csid;
    }

    /**
     * @return IsActive
     */
    public Integer getIsactive() {
        return isactive;
    }

    /**
     * @param isactive
     */
    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
    }

    /**
     * @return CreateTime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return UpdateTime
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}