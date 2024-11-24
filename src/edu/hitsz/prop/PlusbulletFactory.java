package edu.hitsz.prop;

public class PlusbulletFactory implements PropFactory{
    @Override
    public BaseProp createProps(int locationX, int locationY){
        return new PlusbulletProp(locationX, locationY, 0, 5);
    }
}
