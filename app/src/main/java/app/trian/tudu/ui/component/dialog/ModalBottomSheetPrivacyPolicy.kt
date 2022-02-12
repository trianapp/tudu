package app.trian.tudu.ui.component.dialog

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.theme.TuduTheme
import dev.jeziellago.compose.markdowntext.MarkdownText

/**
 * Bottom Sheet Privacy Policy
 * author Trian Damai
 * created_at 12/02/22 - 23.36
 * site https://trian.app
 */
@Composable
fun ModalBottomSheetPrivacyPolicy(
    modifier: Modifier = Modifier,
    onAccept:()->Unit = {}
) {
    Column(
        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 16.dp
            )
    ) {
        Spacer(modifier = modifier.height(20.dp))
        Text(
            text = "Privacy Policy",
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(10.dp))
        MarkdownText(
            markdown = """
         
              ### Privacy Policy of Tudu - Task Planner & To Do Reminders
              
              Please take a moment to familiarize yourself with our Privacy Policy and let us know if you have any questions.
              <br />

              #### Introduction
              This Privacy Policy ("Privacy Policy") describes the privacy practices that we, Tudu - Task Planner & To Do Reminders ("Tudu" or "We"), employ with regard to collecting, using and disclosing information, both personal and non-personal information, which we receive when you use our Services. 
              By using the Services you consent to the practices described in this Privacy Policy.
              IF YOU DO NOT AGREE WITH THE PRACTICES EXPLAINED IN THIS PRIVACY POLICY, DO NOT ACCESS, BROWSE OR USE THE Tudu SERVICES.
              <br />
               We are committed to safeguarding any information collected through the Services. This Privacy Policy is intended to inform you of our policies and procedures regarding the collection, use and disclosure of information on our Services. We also want to inform you about your choices regarding information you share with us. If you have any questions or concerns, please let us know (see "How to contact us" section below).
              <br />As used in this Privacy Policy, the terms "using" and "processing" information include using cookies on a computer or mobile device, subjecting the information to statistical or other analysis and using or handling information in any way, including, but not limited to collecting, storing, evaluating, modifying, deleting, using, combining, disclosing and transferring information within our organization or among our affiliates within the United States or internationally.
              <br />

              ## I. How We Collect Info 

              ### A. We Do Not Collect Personal Information
               Personal Information includes your name, address, email, telephone number, fax number, geographical location, the information you stored on your device, and the information to identify you or other individuals when you use applications, services or websites.
              ### B. Non-Personal Information
               We may collect other information that cannot be used to identify a specific individual (that is, information that is not personal information), for example statistical data generated when you use a specific service, such as user activity (including clicks, page redirects, and browsing time). This information is collected in order to improve the services we provide to you. 
               The type and amount of information collected will depend on how you use our products and/or services. We aggregate this information to help us provide more useful information to our customers as well as to better understand which parts of our websites, products, and services are of most interest to our customers. 
               Aggregated data is considered non-personal information for the purposes of this Privacy Policy.
              ### C. Session and Available Data：
               "Session and Available Data" refers to data information relevant to connections and services that you provide to us when you use an application, service or website, including but not limited to, when you use our applications, services or websites. 
               Session and Available Data includes information related to connection requests, server communication and data sharing, including network testing, quality of service, date, time, and place / location. Gathering of information as described below, we may also gather Session and Available Data. 
               Please note that Session and Available Data does not include any personal information, nor does it include any content that you might use applications (such as photo, storage, camera etc.), services, or website to send or share.
              ### D. Log Data:
                This Log Data may include information such as the browser type or the webpage you were visiting before you came to our Services, pages of our Services that you visit, the time spent on those pages, information you search for on our Services, access times and dates, unique user device serial number, IP address, and other statistics.
                We use this information to monitor and analyze use of the Services and the Services' technical administration, to increase our Services' functionality and user-friendliness, and to better tailor our Services to our visitors' needs.
              ### E. Google and Facebook Analytics
                We may use Google Analytics, Google Firebase and Facebook Analytics to collect information about use of the Services. Google Analytics, Google Firebase and Facebook Analytics collect information such as how often users visit the Services, what activities do they do when they visit the Services, and what are the locations when they login the Services. 
                We only collect aggregate information through Google Analytics and Facebook Analytics, for the purpose of copyright royalty calculation, marketing event implementation, and tailored service provision for users in specific territory. IF YOU DO NOT AGREE WITH THE PRACTICES EXPLAINED IN THIS SECTION, DO NOT ACCESS, BROWSE OR USE THE MY Tudu SERVICES; OR, DELETE THE APP FROM YOUR MOBILE DEVICE AND TERMINATING ALL OTHER USES OF THE SERVICE, IF YOU ALREADY HAD REGISTERED WITH OUR SERVICE.
              ### User Feedback:
                We may collect feedback from you on issues that you report to us, a feedback log, and any email addresses that you provide to us. 
                This information will be used to help us better understand any problems you encounter as well as to allow us to contact you.
                
              ## II. How We Use Collected Info
                <b>Personal Information</b>
                    – Since we do not collect Personal Information, we will not use your Personal Information in any way
             
              
                <b>Non-Personal Information.</b> We may use Non-Personal Information for the following purposes:
                - For Personalized User Experience – We may use your Non-Personal Information to understand your user characteristics, and other trends related to user’s habits;</ul>
                - To Help Improve Our Service – We may use Non-Personal Information to provide, maintain, enhance and improve our applications, services and websites, and the development of new services.</ul>
                - For the Further Development of Tudu - Task Planner & To Do Reminders – We might use Non-Personal Information for market promotion and development purposes</ul>
              </li>

              ## III. Security
                We value your trust in providing us your information, thus we are striving to use commercially acceptable means of protecting it. 
                But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.
              ## IV. Your Rights
              ### A. Settings control
              We recognize that privacy concerns differ from person to person. Therefore, we provide examples of ways My Diary makes available for you to choose to restrict the collection, use and control your privacy settings: read/write access permissions on your phone;
              ### B. Decide whether to use our service when updating the Privacy Policy
              We may modify or update this privacy policy as needed. Therefore, it is recommended that you check this page regularly for any changes. You can decide whether to continue using our services.
              ## V. Third Party Websites and Services
              Our Privacy Policy does not apply to products or services offered by a third party. 
              Tudu may include, depending on which ones you use, third-party products or services such as location-based services. 
              Some products or services may be provided in the form of links to third-party websites, while others may be in the form of SDKs, APIs, etc. 
              Third parties may collect your information when you use such products or services. 
              For this reason, we strongly suggest that you read the third party’s privacy policy just as you have taken the time to read ours. 
              We are not responsible for and cannot control how third parties use the personal information that they collect from you. 
              Our Privacy Policy does not apply to other websites that are linked via our services.
              ## VI. Contact Us
                If you have any questions or feedback regarding this Privacy Policy, or if you have any questions regarding how we collect, use your information, please contact us by emailing triandamai@gmail.com and mention that your message is in reference to the “Privacy Policy”. 
                Our response team will be able to handle any issues you may have about rights requests or questions about your information. 
                If you have a particularly critical issue that needs to be addressed, we may ask you to provide us with more information. 
                If you are not satisfied with the response you receive, you may escalate your complaint to the applicable regulatory authority in your jurisdiction. 
                Upon request, we will provide you with information on how to file a complaint based on your actual circumstances.
          
            """.trimIndent())
        Spacer(modifier = modifier.height(16.dp))
        ButtonPrimary(text = "Accept"){
            onAccept()
        }
        Spacer(modifier = modifier.height(16.dp))
    }

}

@Preview
@Composable
fun PreviewModalBottomSheetPrivacyPolicy() {
    TuduTheme {
        ModalBottomSheetPrivacyPolicy()
    }
}