package com.wwx.minishop.enums;

/**
 *  店铺状态类型
 */
public enum ShopStateEnum {
    CHECK(0,"审核"),OFFLINE(-1,"非法用户"),SUCCESS(1,"操作成功")
    ,PASS(2,"认证通过"),INNER_ERROR(-1001,"系统内部错误")
    ,NULL_SHOP(-1002,"店铺信息为空"),NULL_SHOP_ID(-1003,"店铺id为空"),
    NULL_AREA_ID(-1101,"地区信息为空"),NULL_SHOP_CATEGORY_ID(-1201,"店铺类别为空");

    //状态
    private Integer state;
    //状态信息
    private String stateInfo;

    ShopStateEnum() {
    }

    ShopStateEnum(Integer state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     *   获取枚举值
     * @param state
     * @return
     */
    public static ShopStateEnum getStateOf(int state){
        //values()  获取全部枚举值
        for(ShopStateEnum stateEnum : values()){
            if(stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }
}
