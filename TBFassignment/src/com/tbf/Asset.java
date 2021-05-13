package com.tbf;

/**
 * This class provides a general model for an asset.
 *
 */
public abstract class Asset implements HaveCode{
    private String code;
    private String assetType;
    private String label;

    public Asset(String code, String assetType, String label){
        this.code = code;
        this.assetType = assetType;
        this.label = label;
    }

    public String getCode(){
        return code;
    }

    public String getAssetType(){
        return assetType;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Calculates the return rate of the asset
     * @return
     */
    public double getRateOfReturn(){
        if (getValueAsset() != 0){
            return (this.getAnnualReturn()/ this.getValueAsset()) * 100;
        }else{
            return 0;
        }
    }

    /**
     * Calculates the value of the asset
     * @return
     */
    public abstract double getValueAsset();

    public abstract double getAnnualReturn();

    /**
     * Calculates the risk measure of the asset
     * @return
     */
    public abstract double getRiskMeasure();

    public Asset addData(double numericValue) {
        return null;
    }

    public String toString() {
        return "Asset{" +
                "code='" + code + '\'' +
                ", assetType='" + assetType + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
