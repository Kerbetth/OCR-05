package com.safetynet.SafetyNetAlert.services.dto;

import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.services.dao.JSONDAO;
import org.json.simple.JSONObject;

import java.util.*;

public class DTO {

    private JSONObject allData;
    private ArrayList<Map> dataTypeContent;
    Datatype dataType;

//**************************//
        //Constructor//
//**************************//
    public DTO(Datatype dataType) {
        this.allData = JSONDAO.getJsonData();
        this.dataTypeContent = (ArrayList) JSONDAO.getJsonData().get(dataType.getString());
    }

    public ArrayList<Map> getDataTypeContent(){
    return dataTypeContent;
    }

//**************************//
        //Methods//
//**************************//

    public ArrayList<String> getData(DataEntry entryType) {
        ArrayList<String> dataList = new ArrayList<>();
        for (Map<String, String> inputData : dataTypeContent) {
            switch (entryType) {
                case FNAME:
                    dataList.add(inputData.get(DataEntry.FNAME.getString()));
                    break;
                case LNAME:
                    dataList.add(inputData.get(DataEntry.LNAME.getString()));
                    break;
                case ADDRESS:
                    dataList.add(inputData.get(DataEntry.LNAME.getString()));
                    break;
                case CITY:
                    dataList.add(inputData.get(DataEntry.CITY.getString()));
                    break;
                case ZIP:
                    dataList.add(inputData.get(DataEntry.ZIP.getString()));
                    break;
                case PHONE:
                    dataList.add(inputData.get(DataEntry.PHONE.getString()));
                    break;
                case EMAIL:
                    dataList.add(inputData.get(DataEntry.EMAIL.getString()));
                    break;
                case MEDIC:
                    dataList.add(((Object) inputData.get(DataEntry.MEDIC.getString())).toString());
                    break;
                case ALLERGI:
                    dataList.add(((Object) inputData.get(DataEntry.ALLERGI.getString())).toString());
                    break;
                case AGE:
                    String birthdate = (inputData.get(DataEntry.BIRTHDATE.getString()));
                    System.out.println(birthdate);
                    dataList.add(DTOFactory.getAge(birthdate));
                    break;
            }
        }
        return dataList;
    }

    public void addData(Map inputData) {
        Map defaultData;
        ArrayList<Map> otherData;
        switch (dataType) {
            case MEDREC:
                defaultData = DTOFactory.createdefaultPerson(inputData.get(DataEntry.FNAME.getString()).toString(), inputData.get(DataEntry.LNAME.getString()).toString());
                otherData = (ArrayList) allData.get(Datatype.PERSO.getString());
                otherData.add(defaultData);
                dataTypeContent.add(inputData);
                allData.put(dataType.getString(), dataTypeContent);
                allData.put(Datatype.PERSO.getString(), otherData);
                break;
            case PERSO:
                defaultData = DTOFactory.createdefaultMedicalRecord(inputData.get(DataEntry.FNAME.getString()).toString(), inputData.get(DataEntry.LNAME.getString()).toString());
                otherData = (ArrayList) allData.get(Datatype.MEDREC.getString());
                otherData.add(defaultData);
                dataTypeContent.add(inputData);
                allData.put(dataType.getString(), dataTypeContent);
                allData.put(Datatype.MEDREC.getString(), otherData);
                break;
            case FSTATION:
                dataTypeContent.add(inputData);
                allData.put(dataType.getString(), dataTypeContent);
        }
        JSONDAO.jsonWriter(allData.toString());
    }

    public void setData(Integer id, Map dataToSet) {
        dataTypeContent.set(id, dataToSet);
        allData.put(dataType.getString(), dataTypeContent);
        JSONDAO.jsonWriter(allData.toString());
    }

    public void removeData(Integer id) {
        dataTypeContent.remove(dataTypeContent.get(id));
        allData.put(dataType.getString(), dataTypeContent);
        JSONDAO.jsonWriter(allData.toString());
    }

    public Set<String> getStationAddresses(String stationNumbers) {
        Set<String> stationAddressList = new HashSet<>();
        if (stationNumbers != null) {
            Set<String> stationNumbersSet = new HashSet<>(Arrays.asList(stationNumbers.split(",")));
            for (Map<String, String> firestation : dataTypeContent) {
                for (String stationNumber : stationNumbersSet) {
                    if (firestation.get(DataEntry.STATION.getString()).equals(stationNumber)) {
                        stationAddressList.add(firestation.get(DataEntry.ADDRESS.getString()));
                    }
                }
            }
        } else {
            for (Map<String, String> firestation : dataTypeContent) {
                stationAddressList.add(firestation.get(firestation.get(DataEntry.ADDRESS.getString())));
            }
        }
        return stationAddressList;
    }

    public String getFirestationNumber(String stationAddress) {
        String stationNumber = null;
        if (stationAddress != null) {
            for (Map<String, String> firestation : dataTypeContent) {
                if (firestation.get(DataEntry.ADDRESS.getString()).equals(stationAddress)) {
                    stationNumber = firestation.get(DataEntry.STATION.getString());

                }
            }
        }
        return stationNumber;
    }

    public String getFirestationAddress(Integer stationIndex) {
        String stationAddress;
        if (stationIndex != null) {
            Map firestation = dataTypeContent.get(stationIndex);
            stationAddress = (String) firestation.get(DataEntry.ADDRESS.getString());
        } else {
            stationAddress = null;
        }
        return stationAddress;
    }

    public Integer getIdByAddress(String address) {
        Integer id = 0;
        for (Map<String, String> currentData : dataTypeContent) {
            if (currentData.get(DataEntry.ADDRESS.getString()).equals(address)){
                break;
            } else id++;
        }
        return id;
    }

    public Integer getIdByName(String firstNameLastName) {
        Integer id = 0;
        for (Map<String, String> currentPerson : dataTypeContent) {
            if ((currentPerson.get(DataEntry.FNAME.getString())+(DataEntry.LNAME.getString())).equals(firstNameLastName)){
                break;
            } else id++;
        }
        return id;
    }

}

