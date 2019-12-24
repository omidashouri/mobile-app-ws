package ir.omidashouri.mobileappws.utilities.functions;

/*import ir.org.karnik.mainParts.helper.systemparameter.SystemParameterEnum;
import ir.org.karnik.mainParts.helper.systemparameter.SystemParameterProvider;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.tempuri.SendSoapProxy;*/

/**
 * Created by o.ashouri on 6/8/16.
 */
public class SendSMSFromFarapayamakWebService {
  /*  private static final Logger LOGGER = Logger.getLogger(SendSMSFromWebService.class);

    private static final String END_POINT_URL = SystemParameterProvider.getSystemParameterValue(SystemParameterEnum.SMS_END_POINT_URL_SEND_FARAPAYAMAK);
    static String URN = SystemParameterProvider.getSystemParameterValue(SystemParameterEnum.SMS_END_POINT_URN);
    static String ENQUEUE_METHOD_CALL = "GetCredit";
    static String USER_NAME = SystemParameterProvider.getSystemParameterValue(SystemParameterEnum.SMS_USER_NAME_FARAPAYAMAK);
    static String PASSWORD = SystemParameterProvider.getSystemParameterValue(SystemParameterEnum.SMS_PASSWORD_FARAPAYAMAK);
    static String SENDER_NUMBER = SystemParameterProvider.getSystemParameterValue(SystemParameterEnum.SMS_SENDER_NUMBER_FARAPAYAMAK);
    static String RECIPIENT_NUMBER = "09126607350";
    final static int SMS_LIMIT = 100;




    public SendSMSFromFarapayamakWebService(){

    }


    public String[] sendSimpleSMS(String[] recipientNumbers, String messageText) throws Exception {

        SendSoapProxy sendSoapProxy = new SendSoapProxy();
        String[] recIds;
        Object[] recIdsObject;
        String[] recIdsArray = new String[recipientNumbers.length];
        String[] sendHundredRecIds;
        List<String[]> recIdsList = new ArrayList<>();
        List<String[]> list = splitArray(recipientNumbers, SMS_LIMIT);

        try {

            for (int i = 0; i < list.size(); i++) {
                sendHundredRecIds = new String[list.get(i).length];

                for (int j = 0; j < list.get(i).length; j++) {
                    sendHundredRecIds[j] = list.get(i)[j];
                }
                recIds = sendSoapProxy.sendSimpleSMS(USER_NAME, PASSWORD, sendHundredRecIds, SENDER_NUMBER, messageText, false);
                recIdsList.add(recIds);
            }

                recIdsArray = Arrays.stream(recIdsList.iterator().next()).toArray(String[]::new);

            //5726962982348848215
*//*            System.out.println(" ... ... REC IDS ... ... ");
            Stream.of(recIdsArray).forEach(System.out::println);*//*

        } catch (NullPointerException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e){
            throw new Exception(e);
        }

        return recIdsArray;
    }


    public String[] getDeliveries3(String[] recIdsCode) throws RemoteException {


        SendSoapProxy sendSoapProxy = new SendSoapProxy();
        int[] deliveryIds;
//        recIdsCode = Stream.of(recIdsCode).filter(e->e.length()>4).collect(Collectors.toList()).toArray(new String[0]);

        String[] recIds;
        String[] recIdsArray = new String[recIdsCode.length];
        int[] recIdsInt = new int[recIdsCode.length];
        String[] sendHundredRecIds;
        List<String[]> recIdsList = new ArrayList<>();
        List<String[]> list = splitArray(recIdsCode, SMS_LIMIT);


        for (int i = 0; i < list.size(); i++) {
            sendHundredRecIds = new String[list.get(i).length];

            for (int j = 0; j < list.get(i).length; j++) {
                sendHundredRecIds[j] = list.get(i)[j];
            }
            deliveryIds = sendSoapProxy.getDeliveries3(sendHundredRecIds,USER_NAME,PASSWORD);
//            recIds = new String[deliveryIds.length];
            recIds = Arrays.stream(deliveryIds).mapToObj(String::valueOf).collect(Collectors.toList()).toArray(new String[0]);
            recIdsList.add(recIds);
        }

          recIdsArray = Stream.of(this.integratedArrayString(recIdsList)).collect(Collectors.toList()).toArray(new String[0]);

//        recIdsArray = Arrays.stream(recIdsList.iterator().next()).toArray(String[]::new);
//        Stream.of(this.integratedArray(recIdsList)).collect(Collectors.toList()).toArray(new String[0]);   WRONG
//        recIdsInt = this.integratedArrayInt(recIdsList);

//        deliveryIds =sendSoapProxy.getDeliveries3(recIdsArray,USER_NAME,PASSWORD);

*//*        System.out.println(" ... ... Delivery IDS ... ... ");
            Arrays.stream(recIdsArray).forEach(System.out::println);*//*
        return recIdsArray;
    }

    public <k,v extends Object> Map<k,v> getMapDeliveries3(Map<k,v> inputMap) throws RemoteException {

        String[] recIds;
        int[] deliIds;
        String[] deliIdz;
        Map<k,v> newMap = new HashedMap();

        recIds = inputMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()).toArray(new String[0]);
*//*        deliIds = this.getDeliveries3(recIds);
        deliIdz = Stream.of(deliIds).map(String::valueOf).collect(Collectors.toList()).toArray(new String[0]);*//*

        deliIdz = new String[recIds.length];

        deliIdz = this.getDeliveries3(recIds);

        int index=0;
        for(Map.Entry<k, v> iMap:inputMap.entrySet()){
            newMap.put(iMap.getKey(), (v) deliIdz[index]);
            index++;
        }

        return newMap;
    }


    public <T extends Object> List<T[]> splitArray(T[] array, int max){

        int x = array.length / max;
        int r = (array.length % max); // remainder

        int lower = 0;
        int upper = 0;
        List<T[]> list = new ArrayList<>();
        int i=0;

        for(i=0; i<x; i++){
            upper += max;
            list.add(Arrays.copyOfRange(array, lower, upper));
            lower = upper;
        }

        if(r > 0){
            list.add(Arrays.copyOfRange(array, lower, (lower + r)));
        }
        return list;
    }


    public <T extends Object> T[] integratedArray(List<T[]> arrayList){
        T[] returnArray ;
        List<T> singleList = new ArrayList<T>();
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).length; j++) {
                singleList.add(arrayList.stream().iterator().next()[j]);
            }
        }

        returnArray = (T[]) singleList.stream().toArray();
        return returnArray;
    }

    public String[] integratedArrayString(List<String[]> arrayList){
        String[] returnArray ;
        List<String> singleList = new ArrayList<String>();
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).length; j++) {
                singleList.add(arrayList.stream().iterator().next()[j]);
            }
        }

        returnArray = singleList.stream().map(String::valueOf).toArray(String[]::new);
        return returnArray;
    }

    public int[] integratedArrayInt(List<int[]> arrayList){
        int[] returnArray = new int[this.getListCount(arrayList)];
        List<Integer> singleList = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).length; j++) {
                singleList.add(arrayList.stream().iterator().next()[j]);
            }
        }

        for(Integer i:singleList){
            returnArray[i] = i.intValue();
        }

        return returnArray;
    }


    public int getListCount(List<int[]> arrayList){

        int total=0;

        for (int[] i:arrayList) {
            total+=i.length;
        }
        return total;
    }*/

}
