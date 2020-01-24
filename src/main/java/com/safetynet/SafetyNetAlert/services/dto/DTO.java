package com.safetynet.SafetyNetAlert.services.dto;

import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.services.dao.JSONDAO;

import java.util.*;

public class DTO {

    public Map allData;
    public ArrayList<Map> dataTypeContent;
    private Datatype dataType;
    public JSONDAO jsondao;

//**************************//
        //Constructor//
//**************************//
    public DTO(Datatype dataType) {
        this.dataType = dataType;
        this.jsondao = new JSONDAO();
        this.allData = jsondao.getJsonData();
        this.dataTypeContent = (ArrayList) jsondao.getJsonData().get(dataType.getString());
    }

    public ArrayList<Map> getDataTypeContent(){
    return dataTypeContent;
    }

    public Map getDataTypeContentwithID(Integer id){
        return dataTypeContent.get(id);
    }
//**************************//
        //Methods//
//**************************//

    public ArrayList<Object> getData(DataEntry entryType) {
        ArrayList<Object> dataList = new ArrayList<>();
        for (Map<String, String> inputData : dataTypeContent) {
            switch (entryType) {
                case FNAME:
                    dataList.add(inputData.get(DataEntry.FNAME.getString()));
                    break;
                case LNAME:
                    dataList.add(inputData.get(DataEntry.LNAME.getString()));
                    break;
                case ADDRESS:
                    dataList.add(inputData.get(DataEntry.ADDRESS.getString()));
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
                    dataList.add(inputData.get(DataEntry.MEDIC.getString()));
                    break;
                case ALLERGI:
                    dataList.add(inputData.get(DataEntry.ALLERGI.getString()));
                    break;
                case AGE:
                    String birthdate = (inputData.get(DataEntry.BIRTHDATE.getString()));
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
                break;
        }
        jsondao.jsonWriter(allData);
    }

    public void setData(Integer id, Map dataToSet) {
        dataTypeContent.set(id, dataToSet);
        allData.put(dataType.getString(), dataTypeContent);
        jsondao.jsonWriter(allData);
    }

    public void removeData(Integer id) {
        switch (dataType) {
            case PERSO:
                ArrayList medrecDataOfSamePerson = (ArrayList) allData.get(Datatype.MEDREC.getString());
                dataTypeContent.remove(dataTypeContent.get(id));
                allData.put(dataType.getString(), dataTypeContent);
                medrecDataOfSamePerson.remove(medrecDataOfSamePerson.get(id));
                allData.put(Datatype.MEDREC.getString(), medrecDataOfSamePerson);
                jsondao.jsonWriter(allData);
                break;
            case MEDREC:
                ArrayList personDataOfSamePerson = (ArrayList) allData.get(Datatype.PERSO.getString());
                dataTypeContent.remove(dataTypeContent.get(id));
                allData.put(dataType.getString(), dataTypeContent);
                personDataOfSamePerson.remove(personDataOfSamePerson.get(id));
                allData.put(Datatype.PERSO.getString(), personDataOfSamePerson);
                jsondao.jsonWriter(allData);
                break;
            case FSTATION:
                dataTypeContent.remove(dataTypeContent.get(id));
                allData.put(dataType.getString(), dataTypeContent);
                break;
        }
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

