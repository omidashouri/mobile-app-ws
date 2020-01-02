package ir.omidashouri.mobileappws.utilities;

import ir.omidashouri.mobileappws.domain.User;
import org.springframework.stereotype.Service;

@Service
public class AmazonSES {

    final String FROM = "omidashouri@gmail.com";

    final String SUBJECT = "one last step to complete your registration with PhotoApp";

    final String PASSWORD_RESET_SUBJECT = "Password reset request";

//    The HTML body for the email.
    final String HTMLBODY = "<h1>Please verify your emal address</h1>" +
            "<p>Thank you for registering with our mobile app. To complete registration process do following</p>" +
            "open the following URL in your browser window: " +
            "<a href='http://localhost:8080/v1/users/email-verification?token=$tokenValue'>" +
            "Final step to complete your registration" +"</a><br/><br/>" +
            "Thank you! And we are waiting for you inside!";

//    The email body for recipients with non-HTML email clients.
    final String TEXTBODY = "Please verify your email address. " +
            "Thank you for registering with our application. To complete registration process do following" +
            "Open the following link in your browser window: " +
            " http://localhost:8080/v1/users/email-verification?token=$tokenValue " +
            "Thank you! And we are waiting for you inside!";


    //    The HTML body for the email.
    final String PASSWORD_RESET_HTMLBODY = "<h1>A request to reset your password</h1>" +
            "<p>Hi, $firstName!</p>" +
            "<p>someone has requested to reset your password with our project. if it were not you, please ignore it </p>" +
            "otherwise please click on the link below to set a new password: " +
            "<a href='http://localhost:8080/v1/users/password-reset.html?token=$tokenValue'>" +
            "Click this link to Reset Password" +"</a><br/><br/>" +
            "Thank you!";

    //    The email body for recipients with non-HTML email clients.
    final String PASSWORD_RESET_TEXTBODY = "A request to reset your password " +
            "Hi, $firstName!" +
            "someone has requested to reset your password with our project. if it were not you, please ignore it " +
            "otherwise please open the link below in your browser window to set a new password:" +
            " href='http://localhost:8080/v1/users/password-reset.html?token=$tokenValue " +
            "Thank you!";

//    use this method in 'UserServiceImpl' -> 'createUserDto' method
    public void verifyEmail(User userDomain){

        String htmlBodyWithToken = HTMLBODY.replace("$tokenValue",userDomain.getEmailVerificationToken());
        String textBodyWithToken = TEXTBODY.replace("$tokenValue",userDomain.getEmailVerificationToken());

//        Amazon Class
/*        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(userDomain.getEmail()))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
                                            .withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
                .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
                .withSource(FROM);

        client.sendEmail(request);*/

        System.out.println("Email Sent!");
    }

//    use this method in 'UserServiceImpl' -> 'createUserDto' method
    public boolean sendPasswordResetRequest(String firstName, String email, String token){

        boolean returnValue = false;

//        Amazon Class
/*        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.US_EAST_1).build();*/

        String htmlBodyWithToken = PASSWORD_RESET_HTMLBODY.replace("$tokenValue",token);
                htmlBodyWithToken = htmlBodyWithToken.replace("$firstName",firstName);


        String textBodyWithToken = PASSWORD_RESET_TEXTBODY.replace("$tokenValue",token);
                textBodyWithToken = textBodyWithToken.replace("$firstName",firstName);


/*        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(email))
                .withMessage(new Message()
                        .withBody(new Body()
                            .withHtml(new Content()
                                .withCharset("UTF-8").withData(htmlBodyWithToken))
                            .withText(new Content()
                                .withCharset("UTF-8").withData(textBodyWithToken)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(PASSWORD_RESET_SUBJECT)))
                .withSource(FROM);

        SendEmailResult result = client.sendEmail(request);
        if(result != null && (result.getMessageId() != null && !result.getMessageId().isEmpty()))
        {
            returnValue = true;
        }*/

//      suppose we had sent reset email
        returnValue = true;

        return  returnValue;
    }


}
