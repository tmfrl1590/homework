package com.example.homework.util


enum class ResultResponse(
    val code: ResultCode,
    val message: String,
){
    EXIST_SHOP(ResultCode.ERROR, "이미 존재하는 매장의 이름입니다."),
    NOT_EXIST_SHOP(ResultCode.ERROR, "존재하지 않는 매장입니다."),
    CREATE_SHOP(ResultCode.SUCCESS, "매장생성이 완료되었습니다."),

    NOT_EXIST_PAYMENT_REQUEST(ResultCode.ERROR, "존재하지 않는 요청사항입니다."),
    SUCCESS_PAYMENT_REQUEST(ResultCode.SUCCESS, "결제요청 정보들을 가져왔습니다."),
    SUCCESS_PAYMENT_REQUEST_NODATA(ResultCode.SUCCESS, "결제요청 내역이 없습니다."),
    COMPLETE_PAYMENT_REQUEST(ResultCode.SUCCESS, "결제요청이 완료되었습니다."),
    COMPLETED_PAYMENT_REQUEST(ResultCode.ERROR, "이미 결제가 완료된 요청사항입니다."),

    FAIL_PAYMENT_MONEY(ResultCode.ERROR, "금액이 부족해서 결제 실패했습니다."),
    COMPLETE_PAYMENT(ResultCode.SUCCESS, "결제가 완료되었습니다."),
}

enum class ResultCode(
    val codeName: String,
) {
    SUCCESS(codeName = "성공"),
    ERROR(codeName = "에러"),
}