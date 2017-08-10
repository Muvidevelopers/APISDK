package com.home.apisdk;

/**
 * Created by MUVI on 1/18/2017.
 */

public class APIUrlConstant {

    public static  String BASE_URl;
    public static  String INITIALIZATION_URL = "initialiseSdk";
    public static  String MENU_LIST_URL = "getMenuList";
    public static  String GENRE_LIST_URL = "getGenreList";
    public static  String GET_PROFILE_DETAILS_URL = "getProfileDetails";
    public static  String UPDATE_PROFILE_URL = "updateUserProfile";
    public static  String LOGOUT_URL = "logout";
    public static  String VIDEO_DETAILS_URL = "getVideoDetails";
    public static  String GET_CONTENT_LIST_URL = "getContentList";
    public static  String FORGOT_PASSWORD_URL = "forgotPassword";
    public static  String LOGIN_URL = "login";
    public static  String REGISTER_URL = "registerUser";
    public static  String GET_EPISODE_DETAILS_URL = "episodeDetails";
    public static  String SEARCH_DATA_URL = "searchData";
    public static  String CONTENT_DETAILS_URL = "getContentDetails";


    //New Api Added

    public static  String SUBSCRIPTION_PLAN_LISTS = "getStudioPlanLists";
    public static  String HOMEPAGE_URL = "homePage";
    public static  String GET_FEATURE_CONTENT_URL = "getFeaturedContent";
    public static  String GET_IMAGE_FOR_DOWNLOAD_URL = "GetImageForDownload";
    public static  String CHECK_GEO_BLOCK_URL = "checkGeoBlock";
    public static  String IS_REGISTRATIONENABLED_URL = "isRegistrationEnabled";
    public static  String GETSTATICPAGES_URL = "getStaticPagedetails";
    public static  String CONTACT_US_URL = "contactUs";
    public static  String GET_CELIBRITY_URL = "getCelibrity";
    public static  String PURCHASE_HISTORY_URL = "PurchaseHistory";
    public static  String TRANSACTION_URL = "Transaction";
    public static  String GET_INVOICE_PDF_URL = "GetInvoicePDF";
    public static  String DELETE_INVOICE_PDF_URL = "DeleteInvoicePath";
    public static  String GET_MONETIZATION_DETAILS_URL = "GetMonetizationDetails";
    public static  String SOCIALAUTH_URL = "socialAuth";
    public static  String VALIDATE_VOUCHER_URL = "ValidateVoucher";
    public static  String GET_VOUCHER_PLAN_URL = "GetVoucherPlan";
    public static  String VOUCHER_SUBSCRIPTION_URL = "VoucherSubscription";
    public static  String MYLIBRARY_URL = "MyLibrary";
    public static  String REGISTER_USER_PAYMENT_URL = "registerUserPayment";
    public static  String AUTH_USER_PAYMENT_INFO_URL = "authUserPaymentInfo";
    public static  String GET_CARD_LIST_FOR_PPV_URL = "getCardsListForPPV";
    public static  String LanguageTranslation= "textTranslation";
    public static  String CheckDevice = "CheckDevice";
    public static  String LogoutAll = "LogoutAll";
    public static  String fbUserExistsUrl = "getFbUserStatus";
    public static  String AboutUs = "getStaticPagedetails";

    public static  String ViewFavorite = "ViewFavourite";
    public static  String AddtoFavlist = "AddtoFavlist";
    public static  String DeleteFavList = "DeleteFavList";
    public static  String GetMenusUrl = "GetMenus";
    public static  String UpdateGoogleid = "UpdateGoogleid";
    public static  String GetCastDetails = "getCastDetail";
    public static final String ViewContentRating = "ViewContentRating";
    public static final String AddContentRating = "AddContentRating";
    public static final String addSubscriptionUrl = "ppvpayment";
    public static final String ManageDevices = "ManageDevices";
    public static  String VALIDATE_COUPON_CODE_URL = "validateCouponCode";
    public static final String updateBufferLogUrl = "updateBufferLogs";
    public static  String VIDEO_BUFFER_LOGS_URL = "bufferLogs";
    public static  String VALIDATE_USER_FOR_CONTENT_URL = "isContentAuthorized";
    public static  String IP_ADDRESS_URL = "https://api.ipify.org/?format=json";
    public static  String GET_LANGUAGE_LIST_URL = "getLanguageList";
    public static  String VIDEO_LOGS_URL = "videoLogs";



    public static final String RemoveDevice = "RemoveDevice";


    public static final String CheckIfUserLoggedIn = "CheckIfUserLoggedIn";





    public static String getRemoveDevice() {
        return BASE_URl+RemoveDevice;
    }

    public static String getCheckIfUserLoggedIn() {
        return BASE_URl+CheckIfUserLoggedIn;
    }


    public static String getGetCastDetails() {
        return BASE_URl+GetCastDetails;
    }



    public static String getUpdateGoogleid() {
        return BASE_URl+UpdateGoogleid;
    }


    public static String getGetMenusUrl() {
        return BASE_URl+GetMenusUrl;
    }

    public static String getViewFavorite() {
        return BASE_URl+ViewFavorite;
    }

    public static String getAddtoFavlist() {
        return BASE_URl+AddtoFavlist;
    }

    public static String getDeleteFavList() {
        return BASE_URl+DeleteFavList;
    }

    public static String getAddContentRating() {
        return BASE_URl+AddContentRating;
    }

    public static String getViewContentRating() {
        return BASE_URl+ViewContentRating;
    }

    public static String getAddSubscriptionUrl() {
        return BASE_URl+addSubscriptionUrl;
    }

    public static String getManageDevices() {
        return BASE_URl+ManageDevices;
    }

    public static String getAboutUs() {
        return BASE_URl+AboutUs;
    }


    public static String getFbUserExistsUrl() {
        return BASE_URl+fbUserExistsUrl;
    }



    public static String getLogoutAll() {
        return BASE_URl+LogoutAll;
    }

    public static String getCheckDevice() {
        return BASE_URl+CheckDevice;
    }

    public static String getLanguageTranslation() {
        return BASE_URl+LanguageTranslation;
    }


    public static String getInitializationUrl() {
        return BASE_URl+INITIALIZATION_URL;
    }

    public static String getMenuListUrl() {
        return BASE_URl+MENU_LIST_URL;
    }

    public static String getGenreListUrl() {
        return BASE_URl+GENRE_LIST_URL;
    }

    public static String getGetProfileDetailsUrl() {
        return BASE_URl+GET_PROFILE_DETAILS_URL;
    }

    public static String getUpdateProfileUrl() {
        return BASE_URl+UPDATE_PROFILE_URL;
    }

    public static String getLogoutUrl() {
        return BASE_URl+LOGOUT_URL;
    }

    public static String getVideoDetailsUrl() {
        return BASE_URl+VIDEO_DETAILS_URL;
    }

    public static String getGetContentListUrl() {
        return BASE_URl+GET_CONTENT_LIST_URL;
    }

    public static String getForgotPasswordUrl() {
        return BASE_URl+FORGOT_PASSWORD_URL;
    }

    public static String getLoginUrl() {
        return BASE_URl+LOGIN_URL;
    }

    public static String getRegisterUrl() {
        return BASE_URl+REGISTER_URL;
    }

    public static String getGetEpisodeDetailsUrl() {
        return BASE_URl+GET_EPISODE_DETAILS_URL;
    }

    public static String getSearchDataUrl() {
        return BASE_URl+SEARCH_DATA_URL;
    }

    public static String getContentDetailsUrl() {
        return BASE_URl+CONTENT_DETAILS_URL;
    }

    public static String getSubscriptionPlanLists() {
        return BASE_URl+SUBSCRIPTION_PLAN_LISTS;
    }

    public static String getHomepageUrl() {
        return BASE_URl+HOMEPAGE_URL;
    }

    public static String getGetFeatureContentUrl() {
        return BASE_URl+GET_FEATURE_CONTENT_URL;
    }

    public static String getGetImageForDownloadUrl() {
        return BASE_URl+GET_IMAGE_FOR_DOWNLOAD_URL;
    }

    public static String getCheckGeoBlockUrl() {
        return BASE_URl+CHECK_GEO_BLOCK_URL;
    }

    public static String getIsRegistrationenabledUrl() {
        return BASE_URl+IS_REGISTRATIONENABLED_URL;
    }

    public static String getGetstaticpagesUrl() {
        return BASE_URl+GETSTATICPAGES_URL;
    }

    public static String getContactUsUrl() {
        return BASE_URl+CONTACT_US_URL;
    }

    public static String getGetCelibrityUrl() {
        return BASE_URl+GET_CELIBRITY_URL;
    }

    public static String getPurchaseHistoryUrl() {
        return BASE_URl+PURCHASE_HISTORY_URL;
    }

    public static String getTransactionUrl() {
        return BASE_URl+TRANSACTION_URL;
    }

    public static String getGetInvoicePdfUrl() {
        return BASE_URl+GET_INVOICE_PDF_URL;
    }

    public static String getDeleteInvoicePdfUrl() {
        return BASE_URl+DELETE_INVOICE_PDF_URL;
    }

    public static String getGetMonetizationDetailsUrl() {
        return BASE_URl+GET_MONETIZATION_DETAILS_URL;
    }

    public static String getSocialauthUrl() {
        return BASE_URl+SOCIALAUTH_URL;
    }

    public static String getValidateVoucherUrl() {
        return BASE_URl+VALIDATE_VOUCHER_URL;
    }

    public static String getGetVoucherPlanUrl() {
        return BASE_URl+GET_VOUCHER_PLAN_URL;
    }

    public static String getVoucherSubscriptionUrl() {
        return BASE_URl+VOUCHER_SUBSCRIPTION_URL;
    }

    public static String getMylibraryUrl() {
        return BASE_URl+MYLIBRARY_URL;
    }

    public static String getRegisterUserPaymentUrl() {
        return BASE_URl+REGISTER_USER_PAYMENT_URL;
    }

    public static String getAuthUserPaymentInfoUrl() {
        return BASE_URl+AUTH_USER_PAYMENT_INFO_URL;
    }

    public static String getGetCardListForPpvUrl() {
        return BASE_URl+GET_CARD_LIST_FOR_PPV_URL;
    }

    public static String getValidateCouponCodeUrl() {
        return BASE_URl+VALIDATE_COUPON_CODE_URL;
    }

    public static String getIpAddressUrl() {
        return IP_ADDRESS_URL;
    }

    public static String getGetLanguageListUrl() {
        return BASE_URl+GET_LANGUAGE_LIST_URL;
    }

    public static String getVideoLogsUrl() {
        return BASE_URl+VIDEO_LOGS_URL;
    }

    public static String getVideoBufferLogsUrl() {
        return BASE_URl+VIDEO_BUFFER_LOGS_URL;
    }

    public static String getValidateUserForContentUrl() {
        return BASE_URl+VALIDATE_USER_FOR_CONTENT_URL;
    }


    public static String getUpdateBufferLogUrl() {
        return BASE_URl+updateBufferLogUrl;
    }





}
