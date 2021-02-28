package bgu.spl.net.impl.BGRSServer;

import java.util.Comparator;
import java.util.Vector;

public class Course {
    private int serialNum;
    private short id;
    private String name;
    private int maxReg;
    private Vector<Short> kdams;
    private int currentReg;
    private Vector<String> registeredNames;

    public Course(int _serialNum, short _id, String _name, int _maxReg, Vector<Short> _kdams){
        serialNum=_serialNum;
        id=_id;
        name=_name;
        maxReg=_maxReg;
        currentReg=0;
        kdams = new Vector<Short>();
        if(_kdams != null) {
            for (int i = 0; i < _kdams.size(); i++) {
                kdams.add(_kdams.elementAt(i));
            }
        }
        registeredNames=new Vector<String>();
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public int getMaxReg() {
        return maxReg;
    }

    public String getName() {
        return name;
    }

    public void setKdams(Vector<Short> kdams) {
        this.kdams = kdams;
    }

    public void setMaxReg(int maxReg) {
        this.maxReg = maxReg;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Vector<Short> getKdams(){
        return kdams;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public Vector<String> getRegisteredNames() {
        return registeredNames;
    }

    public int getCurrentReg() {
        return currentReg;
    }
    public boolean register(String userName){
        if(currentReg>=maxReg)
            return false;
        currentReg++;
        registeredNames.add(userName);
        registeredNames.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return true;
    }
    public boolean register(){
        if(currentReg>=maxReg)
            return false;
        currentReg++;
        return true;
    }

    public void unregister(){
        currentReg--;
    }

    public void unregister(String userName){
        currentReg--;
        registeredNames.remove(userName);
    }
}
