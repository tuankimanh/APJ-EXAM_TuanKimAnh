package com.codegym.bank.model;

import java.math.BigDecimal;

/**
 * Lop TRU TUONG (abstract class) dai dien cho mot "Tai khoan ngan hang" noi chung.
 *
 * Day la lop CHA (superclass), chua nhung thong tin va hanh vi DUNG CHUNG
 * cho moi loai tai khoan (Tiet kiem, Thanh toan).
 *
 * Vi sao phai la "abstract"?
 * - Tren thuc te KHONG CO mot "tai khoan ngan hang chung chung" nao ca, no phai la
 *   MOT TRONG HAI loai cu the: Tiet kiem hoac Thanh toan.
 * - Vi vay ta khai bao class nay la abstract de ngan khong cho tao truc tiep
 *   doi tuong kieu TaiKhoanNganHang (vi du: "new TaiKhoanNganHang(...)" se bi LOI BIEN DICH).
 * - Cac lop con (TaiKhoanTietKiem, TaiKhoanThanhToan) se "extends" lop nay
 *   de ke thua toan bo thuoc tinh/phuong thuc dung chung ben duoi.
 */
public abstract class TaiKhoanNganHang {

    // ===================== THUOC TINH DUNG CHUNG CHO MOI TAI KHOAN =====================
    // Dung "protected" (thay vi "private") de cac lop CON (nam trong cung package hoac
    // ke thua) co the truy cap truc tiep neu can, nhung van an toan hon "public".
    protected int id;                 // Id tai khoan - tu dong tang, khong cho nguoi dung tu nhap
    protected String maTaiKhoan;      // Ma tai khoan - do nguoi dung nhap vao
    protected String tenChuTaiKhoan;  // Ten chu so huu tai khoan
    protected String ngayTaoTaiKhoan; // Ngay tao tai khoan, luu duoi dang chuoi dd/MM/yyyy

    /**
     * Constructor (ham khoi tao) cua lop cha.
     * Lop con BAT BUOC phai goi toi constructor nay (thong qua tu khoa "super(...)")
     * de khoi tao cac thuoc tinh dung chung truoc khi khoi tao them thuoc tinh rieng.
     */
    public TaiKhoanNganHang(int id, String maTaiKhoan, String tenChuTaiKhoan, String ngayTaoTaiKhoan) {
        this.id = id;
        this.maTaiKhoan = maTaiKhoan;
        this.tenChuTaiKhoan = tenChuTaiKhoan;
        this.ngayTaoTaiKhoan = ngayTaoTaiKhoan;
    }

    // ===================== GETTER / SETTER =====================
    // Cung cap getter/setter thay vi de thuoc tinh public -> dam bao tinh DONG GOI
    // (encapsulation): du lieu ben trong object chi duoc thay doi thong qua cac
    // phuong thuc duoc kiem soat, khong bi sua truc tiep tu ben ngoai mot cach tuy tien.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTenChuTaiKhoan() {
        return tenChuTaiKhoan;
    }

    public void setTenChuTaiKhoan(String tenChuTaiKhoan) {
        this.tenChuTaiKhoan = tenChuTaiKhoan;
    }

    public String getNgayTaoTaiKhoan() {
        return ngayTaoTaiKhoan;
    }

    public void setNgayTaoTaiKhoan(String ngayTaoTaiKhoan) {
        this.ngayTaoTaiKhoan = ngayTaoTaiKhoan;
    }

    // ===================== HAM TRU TUONG (ABSTRACT METHOD) =====================

    /**
     * Ham TRU TUONG so 1: KHONG co phan than ham (khong co dau { }), chi co khai bao.
     * Day chinh la yeu cau "abstract method" cua de bai.
     *
     * Moi lop con (TaiKhoanTietKiem / TaiKhoanThanhToan) BAT BUOC phai "override"
     * (ghi de) ham nay de tra ve chuoi mo ta cac THONG TIN RIENG cua loai tai khoan do.
     * - TaiKhoanTietKiem -> tra ve: so tien gui tiet kiem, ngay gui, lai suat, ky han.
     * - TaiKhoanThanhToan -> tra ve: so the, so tien trong tai khoan.
     */
    public abstract String layThongTinRieng();

    /**
     * Ham TRU TUONG so 2: moi loai tai khoan se sinh ra 1 dong du lieu CSV khac nhau
     * (tai khoan tiet kiem co 8 cot, tai khoan thanh toan co 6 cot), nen phai de
     * moi lop con tu quyet dinh cach ghi dong CSV cua rieng minh.
     */
    public abstract String toCsvLine();

    /**
     * Override lai phuong thuc toString() cua lop Object (lop cha cua moi class trong Java).
     *
     * Ham nay in ra cac thong tin DUNG CHUNG truoc (id, ma tai khoan, ten chu, ngay tao),
     * sau do GOI toi layThongTinRieng() de lay them phan thong tin RIENG.
     *
     * Diem hay o day la TINH DA HINH (polymorphism): du bien dang khai bao kieu
     * TaiKhoanNganHang, nhung khi goi layThongTinRieng(), Java se tu dong chay
     * dung phien ban ham cua lop con thuc su (TaiKhoanTietKiem hay TaiKhoanThanhToan)
     * ma khong can phai kiem tra "if (obj instanceof ...)" o ben ngoai.
     */
    @Override
    public String toString() {
        return "Id tai khoan      : " + id + "\n" +
                "Ma tai khoan      : " + maTaiKhoan + "\n" +
                "Ten chu tai khoan : " + tenChuTaiKhoan + "\n" +
                "Ngay tao tai khoan: " + ngayTaoTaiKhoan + "\n" +
                layThongTinRieng();
    }

    /**
     * Ham ho tro (helper) DUNG CHUNG cho ca TaiKhoanTietKiem va TaiKhoanThanhToan.
     *
     * Ly do can ham nay: neu dung truc tiep "" + soTien (noi chuoi voi mot so double),
     * Java se tu dong goi Double.toString(), va voi cac so LON (vi du 15000000.0)
     * ket qua se bi hien thi duoi dang KHOA HOC gay kho doc, vi du "1.5E7" thay vi "15000000".
     *
     * Ham nay dung BigDecimal de chuyen so double ve dang CHUOI SO THAP PHAN BINH THUONG
     * (khong co ky hieu E / khong co so mu), va cat bo cac so 0 du thua o cuoi
     * (vi du 5000000.0 -> "5000000" thay vi "5000000.0").
     */
    protected static String formatSo(double so) {
        return new BigDecimal(String.valueOf(so)).stripTrailingZeros().toPlainString();
    }
}
