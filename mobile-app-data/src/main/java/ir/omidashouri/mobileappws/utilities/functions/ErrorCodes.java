package ir.omidashouri.mobileappws.utilities.functions;

/**
 * Created by IntelliJ IDEA.
 * User: amin
 * Date: Jun 29, 2010
 * Time: 2:25:47 PM
 * contains basic information about error codes which client might receive during a webservice call
 */
public enum ErrorCodes {
    INVALID_RECIPIENT_NUMBER(1, "the Strings You presented as recipient numbers are not valid phone numbers, please check'em again"),
    INVALID_SENDER_NUMBER(2, "the Strings You presented as sender numbers(3000-blah blah blahs) are not valid numbers, please check'em again"),
    INVALID_ENCODING(3,"are You sure You've entered the right encoding for this message? You can try other encodings to bypass this error code"),
    INVALID_MESSAGE_CLASS(4,"entered MessageClass is not valid. for a normal MClass, leave this entry empty"),
    INVALID_UDH(6, "entered UDH is invalid. in order to send a simple message, leave this entry empty"),
    INVALID_ACCOUNT_ID(12, "you're trying to use a service from another account??? check your UN/Password/NumberRange again"),
    NULL_MESSAGE(13, "check the text of your message. it seems to be null."),
    CREDIT_NOT_ENOUGH(14, "Your credit's not enough to send this message. you might want to buy some credit.call "),
    SERVER_ERROR(15, "something bad happened on server side, you might want to call MAGFA Support about this:"),
    ACCOUNT_INACTIVE(16, "Your account is not active right now, call -- to activate it"),
    ACCOUNT_EXPIRED(17, "looks like Your account's reached its expiration time, call -- for more information"),
    INVALID_USERNAME_PASSWORD_DOMAIN(18, "the combination of entered Username/Password/Domain is not valid. check'em again"),
    AUTHENTICATION_FAILED(19, "You're not entering the correct combination of Username/Password"),
    SERVICE_TYPE_NOT_FOUND(20, "check the service type you're requesting. we don't get what service you want to use."),
    ACCOUNT_SERVICE_NOT_FOUND(22, "your current number range doesn't have the permission to use Webservices"),
    SERVER_BUSY(23,"Sorry, Server's under heavy traffic pressure, try testing another time please"),
    INVALID_MESSAGE_ID(24, "entered message-id seems to be invalid, are you sure You entered the right thing?"),
    ACCOUNT_SERVICE_NOT_ACCEPTABLE(25, "entered message-id seems to be invalid, check the version of written method."),
    RECEIVER_NUMBER_BLACK_LIST(27, "receiver number seems to be invalid, receiver number is in black list service provider."),
    
    
    WEB_RECIPIENT_NUMBER_ARRAY_IS_NULL(106,"array of recipient numbers must have at least one member"),
    WEB_RECIPIENT_NUMBER_ARRAY_TOO_LONG(107, "the maximum number of recipients per message is 90"),
    WEB_SENDER_NUMBER_ARRAY_IS_NULL(108, "array of sender numbers must have at least one member"),
    WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_SENDER_NUMBER_ARRAY(103, "This error happens when you have more than one " +
            "sender-number for message. when you have more than one sender number, for each sender-number you must " +
            "define a recipient number..."),
    WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_MESSAGE_ARRAY(101, "when you have N > 1 texts to send, you have to define N recipient-numbers..."),
    WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_UDH_ARRAY(104, "this happens when you try to define UDHs for your messages. in this case you must define one recipient number for each udh"),
    WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_MESSAGE_CLASS_ARRAY(102, "this happens when you try to define MClasses for your messages. in this case you must define one recipient number for each MClass"),
    WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_ENCODING_ARRAY(109, "this happens when you try to define encodings for your messages. in this case you must define one recipient number for each Encoding"),
    WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_CHECKING_MESSAGE_IDS__ARRAY(110,"this happens when you try to define checking-message-ids for your messages. in this case you must define one recipient number for each checking-message-id");
    final String description;
    final int code;
    ErrorCodes(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public int getCode() {
        return code;
    }

    public static String getDescriptionForCode(int code){
        switch (code){
            case 1 : return INVALID_RECIPIENT_NUMBER.description;
            case 2 : return INVALID_SENDER_NUMBER.description;
            case 3 : return INVALID_ENCODING.description;
            case 4 : return INVALID_MESSAGE_CLASS.description;
            case 6 : return INVALID_UDH.description;
            case 12 : return INVALID_ACCOUNT_ID.description;
            case 13 : return NULL_MESSAGE.description;
            case 14 : return CREDIT_NOT_ENOUGH.description;
            case 15 : return SERVER_ERROR.description;
            case 16 : return ACCOUNT_INACTIVE.description;
            case 17 : return ACCOUNT_EXPIRED.description;
            case 18 : return INVALID_USERNAME_PASSWORD_DOMAIN.description;
            case 19 : return AUTHENTICATION_FAILED.description;
            case 20 : return SERVICE_TYPE_NOT_FOUND.description;
            case 22 : return ACCOUNT_SERVICE_NOT_FOUND.description;
            case 23 : return SERVER_BUSY.description;
            case 24 : return INVALID_MESSAGE_ID.description;
            case 25 : return ACCOUNT_SERVICE_NOT_ACCEPTABLE.description;
            case 27 : return RECEIVER_NUMBER_BLACK_LIST.description;
            case 106 : return WEB_RECIPIENT_NUMBER_ARRAY_IS_NULL.description;
            case 107 : return WEB_RECIPIENT_NUMBER_ARRAY_TOO_LONG.description;
            case 108 : return WEB_SENDER_NUMBER_ARRAY_IS_NULL.description;
            case 103 : return WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_SENDER_NUMBER_ARRAY.description;
            case 101 : return WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_MESSAGE_ARRAY.description;
            case 104 : return WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_UDH_ARRAY.description;
            case 102 : return WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_MESSAGE_CLASS_ARRAY.description;
            case 109 : return WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_ENCODING_ARRAY.description;
            case 110 : return WEB_RECIPIENT_NUMBER_ARRAY_SIZE_NOT_EQUAL_CHECKING_MESSAGE_IDS__ARRAY.description;
            default: return "";
        }
    }
} 