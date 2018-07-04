package com.medoske.www.redhealpatient.Api;

/**
 * Created by USER on 10.5.17.
 */

public class Apis {
// ip address

    public final static String IP_ADDRESS_URL ="http://192.168.0.100/";

   // public final static String IP_ADDRESS_URL ="http://49.205.239.114/";

   // public final static String IP_ADDRESS_URL ="http://redheal.com/";

    // base url
    public final static String BASEURL = IP_ADDRESS_URL+"redhealPatient/index.php/api/patient";

    // base image url
    public final static String IMAGE_BASEURL =IP_ADDRESS_URL+"redhealPatient/uploads/";

    // Doctor base image url
    public final static String DOCTOR_IMAGE_BASEURL =IP_ADDRESS_URL+"rhdoctor/uploads/profile/";


    // login get url
    public final static String LOGIN_URL = BASEURL+"/patients/";
    // register post url
    public final static String REGISTER_URL = BASEURL+"/patients";
    // otp url
    public final static String OTP_URL = BASEURL+"/otpVerification/";

    // otp url
    public final static String PUSH_NOTIFICATION_URL = BASEURL+"/addfcm/";

    // family get url
    public final static String FAMILY_URL = BASEURL+"/families/";

    // location post url
    public final static String ADD_LOCATION_URL = BASEURL+"/locations";
    // location get url
    public final static String LOCATION_URL = BASEURL+"/locations/";

    // reports post url
    public final static String ADD_REPORTS_URL = BASEURL+"/prescription";
    // prescription get url
    public final static String PRESCRIPTION_REPORTS_URL = BASEURL+"/prescription/";
    // diagnostics get url
    public final static String DIAGNOSTIC_REPORTS_URL = BASEURL+"/diagnostics/";
    // precounsultaion get url
    public final static String PRECOUNSULTATION_REPORTS_URL = BASEURL+"/preconsultation/";

    // nearest doctors get url
   // public final static String DOCTORS_LIST_URL = BASEURL+"/doctors/";
    // doctor service get url
    public final static String DOCTORS_SERVICES_URL = IP_ADDRESS_URL+"redhealDoctor/index.php/api/doctor/service/";
    // doctor specialization get url
    public final static String DOCTORS_SPECILIZATION_URL = IP_ADDRESS_URL+"redhealDoctor/index.php/api/doctor/specialization/";
    // doctor education get url
    public final static String DOCTORS_EDUCATION_URL = IP_ADDRESS_URL+"redhealDoctor/index.php/api/doctor/education/";
    // doctor experience get url
    public final static String DOCTORS_EXPERIENCE_URL = IP_ADDRESS_URL+"redhealDoctor/index.php/api/doctor/experience/";
    // doctor recognization get url
    public final static String DOCTORS_RECOGNIZATION_URL = IP_ADDRESS_URL+"redhealDoctor/index.php/api/doctor/awards/";
    // doctor membership get url
    public final static String DOCTORS_MEMBERSHIP_URL = IP_ADDRESS_URL+"redhealDoctor/index.php/api/doctor/membership/";

    // doctor clinics get url
    public final static String CLINIC_URL = BASEURL+"/appointment/hospitalsList/";
    //  fixAppointment post url
    public final static String FIX_APPOINTMENT_URL = BASEURL+"/appointment";

    // fix Spinner  get url
    public final static String FIX_SPINNER_URL = BASEURL+"/timings/";

    // health tips  get url
    public final static String HEALTH_TIP_URL = BASEURL+"/healthtips";

    // base Diagnostic image url
    public final static String DIAGNOSTIC_IMAGE_URL =IP_ADDRESS_URL+"rhdiagnosticsui/uploads/profile/";
    // Diagnostics list  get url
    public final static String DIAGNOSTICS_CENTER_URL = BASEURL+"/diagnostics/";
    // Diagnostics_packages list  get url
    public final static String DIAGNOSTICS_PACKEGES_URL = BASEURL+"/diagnosticpackages/";

    public final static String DIAGNOSTICS_CATAGOERY_URL = BASEURL+"/diagnostictests/";
    // intrest packages post url
    public final static String INTREST_DIAGNOSTICS_URL = IP_ADDRESS_URL+"redhealPatient/index.php/api/patient/intrestdiagnosticpackage/";


    // base hospitals image url
    public final static String HOSPITAL_IMAGE_URL =IP_ADDRESS_URL+"hospitals/uploads/profile/";
    // hospitals list  get url
    public final static String HOSPITALS_LIST_URL = IP_ADDRESS_URL+"redhealHospitals/index.php/api/hospital/hospitalslist";
    // hospitals_packages list  get url
    public final static String HOSPITALS_PACKEGES_URL = IP_ADDRESS_URL+"redhealHospitals/index.php/api/hospital/packages/";
    // intrest packages post url
    public final static String INTREST_PACKEGES_URL = IP_ADDRESS_URL+"redhealPatient/index.php/api/patient/interestpackages";

    // base hospitals image url
    public final static String INSURANCE_IMAGE_URL =IP_ADDRESS_URL+"redhealInsurance/uploads/insurance/";
    // insurance list  get url
    public final static String INSURANCE_LIST_URL = IP_ADDRESS_URL+"redhealInsurance/index.php/api/insurance/insuranceslist";
    // insurance_details list  get url
    public final static String INSURANCE_PLANS_URL = IP_ADDRESS_URL+"redhealpatient/index.php/api/patient/interestplans";

    // my_appointments list  get url
    public final static String MY_APPOINTMENTS_URL = IP_ADDRESS_URL+"redhealPatient/index.php/api/patient/appointment/";
    // my_doctors list  get url
    public final static String MY_DOCTORS_URL = IP_ADDRESS_URL+"redhealPatient/index.php/api/patient/mydoctors/";




    public final static String MY_VISITS_URL = BASEURL+"/visits/";

  //  public final static String EDIT_PROFILE_URL = BASEURL+"/patients";

    public final static String MY_PROFILE_URL = BASEURL+"/editpatient/";


    public final static String PRESENR_LATLONG_URL = BASEURL+"/newlead";

    public final static String SPECLIZATION_URL = BASEURL+"/specialization";

    public final static String TOP_SPECLIZATION_URL = BASEURL+"/specialization/topspecialities";

    public final static String DOCTORS_SEARCH_URL = BASEURL+"/searchDoctor/";

    public final static String CLINIC_SEARCH_URL = BASEURL+"/searchClinic/";

    public final static String SPECLIZATION_SEARCH_URL = BASEURL+"/searchSpecialization/";

    public final static String SELECT_CLINIC_URL = BASEURL+"/clinicsByDoctorRedhealId/";

    public final static String TIMESLOT_MORNING_URL = BASEURL+"/clinicmorningtimeslots/";
    public final static String TIMESLOT_AFTERNOON_URL = BASEURL+"/clinicafternoontimeslots/";
    public final static String TIMESLOT_EVENG_URL = BASEURL+"/cliniceveningtimeslots/";

    public final static String TIMESLOT_MORNING_INSTANT_URL = BASEURL+"/clinicInstantTimeSlotsMorning/";
    public final static String TIMESLOT_AFTERNOON_INSTANT_URL = BASEURL+"/clinicInstantTimeSlotsAfternoon/";
    public final static String TIMESLOT_EVENG_INSTANT_URL = BASEURL+"/clinicInstantTimeSlotsEvening/";

    public final static String TIMESLOT_PACKAGE_URL = BASEURL+"/packageInstantTimeSlots/";

    public final static String CONFIRM_BOOK_URL = BASEURL+"/appointment/";

    public final static String PACKAGE_CONFIRM_BOOK_URL = BASEURL+"/bookpackage/";

    public final static String CONFIRM_APPOINTMENT_URL = BASEURL+"/confirmappointment/";

    public final static String PAY_APPOINTMENT_URL = BASEURL+"/appointmentpayment/";

    public final static String CONFIRM_PACKAGES_URL = BASEURL+"/confirmpackage/";

    public final static String ABOUT_CLINIC_URL = BASEURL+"/aboutclinic/";

    public final static String ABOUT_ME_URL = BASEURL+"/aboutme/";


    public final static String ALL_PACKAGES_URL = BASEURL+"/allpackages/";

    public final static String BEST_PACKAGES_URL = BASEURL+"/bestpackages/";

    public final static String BEST_ALL_PACKAGES_URL = BASEURL+"/allbestpackages/";

    public final static String PACKAGES__DETAILS_URL = BASEURL+"/packageprofile/";

    public final static String ALL_FEEDS_URL = BASEURL+"/allfeeds/";

    public final static String CLINIC_PROFILE_URL = BASEURL+"/clinicDetails/";

    public final static String DOCTORS_LIST_URL = BASEURL+"/specializationIdByDoctor/";

    public final static String FEEDS_LIST_URL = BASEURL+"/feeds/";

    public final static String CURRENT_APPOINTMENTS_LIST_URL = BASEURL+"/mycurrentappointments/";

    public final static String PAST_APPOINTMENTS_LIST_URL = BASEURL+"/mypastappointments/";

    public final static String PAST_APPOINTMENTS_DETAILS_URL = BASEURL+"/mypastappointmentdetails/";

    public final static String ADD_REPORT_URL = BASEURL+"/report/";

    public final static String MY_PACKEGES_URL = BASEURL+"/mypackages/";

    public final static String MY_PACKEGES_DETAILS_URL = BASEURL+"/packagedetails/";

    public final static String PACKEGES_PAYMENT_URL = BASEURL+"/packagepayment/";

    public final static String MY_FAV_DOCTOR_URL = BASEURL+"/myfavdoctor/";

    public final static String MY_FAV_DOCTORS_LIST_URL = BASEURL+"/myfavdoctorslist/";

    public final static String MY_FAV_UPDATE_URL = BASEURL+"/myfavfeed/";

    public final static String MY_FAV_TIP_LIST_URL = BASEURL+"/myfavfeedslist/";

    public final static String MY_FAMILY_LIST_URL = BASEURL+"/myfamilylist/";

    public final static String ADD_FAMILY_URL = BASEURL+"/addfamily/";

    public final static String ADD_RELATION_URL = BASEURL+"/relationadd/";

    public final static String CHECK_FAMILY_URL = BASEURL+"/relationcheck/";

    public final static String MY_REPORTS_URL = BASEURL+"/myreports/";

    public final static String MY_LAB_REPORTS_URL = BASEURL+"/mylabreports/";

    public final static String MY_LAB_REPORTS_Details_URL = BASEURL+"/mylabreportdetails/";

    public final static String EDIT_PROFILE_URL = BASEURL+"/patients/";

    public final static String RELATION_TYPE_URL = BASEURL+"/relationTypes/";

    public final static String PATIENT_RELATION_URL = BASEURL+"/patientRelation/";

    public final static String PATIENT_RELATION_DETAILS_URL = BASEURL+"/patientDetails/";

    public final static String NOTIFICATION_DOCTORS_URL = BASEURL+"/shareddoctors/";

    public final static String ALL_NOTIFICATION_URL = BASEURL+"/notifications/";

    public final static String DOCTOR_PAYMENT_MODE_URL = BASEURL+"/doctorpaymentmethods/";

    public final static String CANCEL_APPOINTMENT_URL = BASEURL+"/cancelappointment/";

    public final static String DIAGNOSTICS_RECOMMENDATION_URL = BASEURL+"/dgsrtests/";

    public final static String CATEGOERY_LIST_URL = BASEURL+"/dgscategorytests/";

}

