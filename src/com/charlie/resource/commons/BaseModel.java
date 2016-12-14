package com.charlie.resource.commons;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.charlie.resource.enums.SysStatus;
import com.charlie.resource.util.DateUtil;

public class BaseModel {

    private Long sysid = Long.valueOf(-1);
    private SysStatus sysstatus = SysStatus.Y;
    private String syscreator = "";
    private Timestamp syscreatetime = DateUtil.time99991231();
    private String sysmodifier = "";
    private Timestamp sysmodifytime = DateUtil.time99991231();
    private Timestamp sysbegintime = DateUtil.time99991231();
    private Timestamp sysendtime = DateUtil.time99991231();

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(
                "sysid", sysid).append("sysstatus", sysstatus).append(
                "syscreator", syscreator).append("syscreatetime", syscreatetime).append("sysmodifier",
                sysmodifier).append("sysmodifytime", sysmodifytime).append("sysbegintime", sysbegintime).append("sysendtime",
                sysendtime).toString();
    }

    public Long getSysid() {
        if ( sysid == null ) {
            sysid = Long.valueOf(-1);
        }
        return sysid;
    }

    public void setSysid(Long sysid) {
        this.sysid = sysid;
    }

    public SysStatus getSysstatus() {
        return sysstatus;
    }

    public void setSysstatus(SysStatus sysstatus) {
        this.sysstatus = sysstatus;
    }

    public String getSyscreator() {
        if ( syscreator == null ) {
            syscreator = "";
        }
        return syscreator;
    }

    public void setSyscreator(String syscreator) {
        this.syscreator = syscreator;
    }

    public Timestamp getSyscreatetime() {
        if ( syscreatetime == null ) {
            syscreatetime = DateUtil.time99991231();
        }
        return syscreatetime;
    }

    public void setSyscreatetime(Timestamp syscreatetime) {
        this.syscreatetime = syscreatetime;
    }

    public String getSysmodifier() {
        if ( sysmodifier == null ) {
            sysmodifier = "";
        }
        return sysmodifier;
    }

    public void setSysmodifier(String sysmodifier) {
        this.sysmodifier = sysmodifier;
    }

    public Timestamp getSysmodifytime() {
        if ( sysmodifytime == null ) {
            sysmodifytime = DateUtil.time99991231();
        }
        return sysmodifytime;
    }

    public void setSysmodifytime(Timestamp sysmodifytime) {
        this.sysmodifytime = sysmodifytime;
    }

    public Timestamp getSysbegintime() {
        if ( sysbegintime == null ) {
            sysbegintime = DateUtil.time99991231();
        }
        return sysbegintime;
    }

    public void setSysbegintime(Timestamp sysbegintime) {
        this.sysbegintime = sysbegintime;
    }

    public Timestamp getSysendtime() {
        if ( sysendtime == null ) {
            setSysendtime(DateUtil.time99991231());
        }
        return sysendtime;
    }

    public void setSysendtime(Timestamp sysendtime) {
        this.sysendtime = sysendtime;
    }

}
