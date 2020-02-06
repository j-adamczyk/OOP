package model;

/**
 * Instances of this class are used as types to represent air index (quality). First attribute (stIndexLevel) stands for overall air index and further
 * attributes for their respective parameters (e. t. c66h6IndexLevel for C6H6 index).
 */
public class AirIndex
{
    private String stIndexLevel;
    private String so2IndexLevel;
    private String no2IndexLevel;
    private String coIndexLevel;
    private String pm10IndexLevel;
    private String pm25IndexLevel;
    private String o3IndexLevel;
    private String c6h6IndexLevel;

    public String getStIndexLevel() {
        return stIndexLevel;
    }

    public void setStIndexLevel(String stIndexLevel) {
        this.stIndexLevel = stIndexLevel;
    }

    public String getSo2IndexLevel() {
        return so2IndexLevel;
    }

    public void setSo2IndexLevel(String so2IndexLevel) {
        this.so2IndexLevel = so2IndexLevel;
    }

    public String getNo2IndexLevel() {
        return no2IndexLevel;
    }

    public void setNo2IndexLevel(String no2IndexLevel) {
        this.no2IndexLevel = no2IndexLevel;
    }

    public String getCoIndexLevel() {
        return coIndexLevel;
    }

    public void setCoIndexLevel(String coIndexLevel) {
        this.coIndexLevel = coIndexLevel;
    }

    public String getPm10IndexLevel() {
        return pm10IndexLevel;
    }

    public void setPm10IndexLevel(String pm10IndexLevel) {
        this.pm10IndexLevel = pm10IndexLevel;
    }

    public String getPm25IndexLevel() {
        return pm25IndexLevel;
    }

    public void setPm25IndexLevel(String pm25IndexLevel) {
        this.pm25IndexLevel = pm25IndexLevel;
    }

    public String getO3IndexLevel() {
        return o3IndexLevel;
    }

    public void setO3IndexLevel(String o3IndexLevel) {
        this.o3IndexLevel = o3IndexLevel;
    }

    public String getC6h6IndexLevel() {
        return c6h6IndexLevel;
    }

    public void setC6h6IndexLevel(String c6h6IndexLevel) {
        this.c6h6IndexLevel = c6h6IndexLevel;
    }
}