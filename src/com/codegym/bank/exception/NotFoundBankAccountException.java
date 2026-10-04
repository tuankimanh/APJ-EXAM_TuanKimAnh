package com.codegym.bank.exception;

/**
 * Exception (ngoai le) TU DINH NGHIA (custom exception).
 *
 * Duoc "throw" (nem) ra khi nguoi dung nhap vao mot Ma tai khoan KHONG TON TAI
 * trong danh sach tai khoan ngan hang (vi du: khi thuc hien chuc nang Xoa).
 *
 * Lop nay ke thua tu "Exception" (khong phai "RuntimeException") nen day la
 * mot CHECKED EXCEPTION: bat ky phuong thuc nao co the throw ra loai exception nay
 * deu phai khai bao "throws NotFoundBankAccountException" o chu ky ham, va noi
 * goi phuong thuc do BAT BUOC phai xu ly bang try-catch (neu khong trinh bien dich se bao loi).
 * Dieu nay giup dam bao loi "tai khoan khong ton tai" luon duoc xu ly ro rang,
 * khong bi quen sot trong code.
 */
public class NotFoundBankAccountException extends Exception {

    /**
     * Constructor nhan vao noi dung thong diep loi (message), vi du: "Tai khoan khong ton tai."
     * roi truyen message do len cho constructor cua lop cha (Exception) thong qua "super(message)".
     * Nho vay, khi bat exception va goi e.getMessage(), ta se nhan lai dung noi dung nay.
     */
    public NotFoundBankAccountException(String message) {
        super(message);
    }
}
