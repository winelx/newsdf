package com.example.administrator.newsdf.Bean;


import com.example.administrator.newsdf.TreeView.inface.TreeNodeGroup;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeId;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeIsLeaf;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeLabel;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeNumber;
import com.example.administrator.newsdf.TreeView.inface.TreeNodePhone;
import com.example.administrator.newsdf.TreeView.inface.TreeNodePid;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeTitle;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeTypes;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeUserID;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeUsername;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeisparent;
import com.example.administrator.newsdf.TreeView.inface.TreeNodeiswas;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class OrganizationEntity {
    @TreeNodeLabel
    private String departname;
    @TreeNodePid
    private String parentId;
    @TreeNodeIsLeaf
    private String isleaf;
    private String longnumber;
    private String levels;
    private String counts;
    @TreeNodeId
    private String id;
    @TreeNodeiswas
    private boolean iswbs;
    @TreeNodeisparent
    private boolean isparent;
    @TreeNodeTypes
    private String types;
    @TreeNodeUsername
    private String username;
    @TreeNodeNumber
    private String number;
    @TreeNodeUserID
    private String userId;

    @TreeNodeTitle
    private String title;
    @TreeNodePhone
    private String phone;
    @TreeNodeGroup
    private boolean isDrawingGroup;

    public OrganizationEntity(String _id, String _parentId, String _name, String isleaf,
                              boolean iswbs, boolean isparent, String type, String username,
                              String number, String userId, String title, String phone, boolean isDrawingGroup) {
        this.id = _id;
        this.parentId = _parentId;
        this.departname = _name;
        this.isleaf = isleaf;
        this.iswbs = iswbs;
        this.isparent = isparent;
        this.types = type;
        this.username = username;
        this.number = number;
        this.userId = userId;
        this.title = title;
        this.phone = phone;
        this.isDrawingGroup = isDrawingGroup;

    }

    public OrganizationEntity() {

    }

    public boolean iswbs() {
        return iswbs;
    }

    public void setIswbs(boolean iswbs) {
        this.iswbs = iswbs;
    }

    public boolean isparent() {
        return isparent;
    }

    public void setIsparent(boolean isparent) {
        this.isparent = isparent;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    public String getLongnumber() {
        return longnumber;
    }

    public void setLongnumber(String longnumber) {
        this.longnumber = longnumber;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDrawingGroup() {
        return isDrawingGroup;
    }

    public void setDrawingGroup(boolean drawingGroup) {
        isDrawingGroup = drawingGroup;
    }
}
