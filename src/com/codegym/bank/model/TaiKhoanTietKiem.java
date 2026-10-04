package com.codegym.bank.model;

/**
 * Lop TaiKhoanTietKiem (Tai khoan tiet kiem).
 *
 * "extends TaiKhoanNganHang" nghia la lop nay KE THUA tu lop cha TaiKhoanNganHang:
 * - Tu dong co san id, maTaiKhoan, tenChuTaiKhoan, ngayTaoTaiKhoan va cac getter/setter
 *   tuong ung, KHONG CAN khai bao lai.
 * - Chi can khai bao THEM cac thuoc tinh rieng cua tai khoan tiet kiem.
 * - Vi TaiKhoanNganHang la abstract, nen lop nay la lop "cu the" (concrete class)
 *   dau tien co the duoc tao doi tuong bang "new TaiKhoanTietKiem(...)".
 */
public class TaiKhoanTietKiem extends TaiKhoanNganHang {

    // ===================== THUOC TINH RIENG CUA TAI KHOAN TIET KIEM =====================
    private double soTienGuiTietKiem; // So tien gui tiet kiem (bat buoc la so duong)
    private String ngayGuiTietKiem;   // Ngay gui tiet kiem, dang chuoi dd/MM/yyyy
    private double laiSuat;           // Lai suat (%/nam)
    private int kyHan;                // Ky han gui, tinh theo so thang

    /**
     * Constructor cua TaiKhoanTietKiem.
     * Dong dau tien goi "super(...)" de nho lop cha khoi tao truoc 4 thuoc tinh dung chung,
     * sau do moi gan tiep cac thuoc tinh rieng cua lop nay.
     */
    public TaiKhoanTietKiem(int id, String maTaiKhoan, String tenChuTaiKhoan, String ngayTaoTaiKhoan,
                             double soTienGuiTietKiem, String ngayGuiTietKiem, double laiSuat, int kyHan) {
        super(id, maTaiKhoan, tenChuTaiKhoan, ngayTaoTaiKhoan);
        this.soTienGuiTietKiem = soTienGuiTietKiem;
        this.ngayGuiTietKiem = ngayGuiTietKiem;
        this.laiSuat = laiSuat;
        this.kyHan = kyHan;
    }

    // ===================== GETTER / SETTER RIENG =====================

    public double getSoTienGuiTietKiem() {
        return soTienGuiTietKiem;
    }

    public void setSoTienGuiTietKiem(double soTienGuiTietKiem) {
        this.soTienGuiTietKiem = soTienGuiTietKiem;
    }

    public String getNgayGuiTietKiem() {
        return ngayGuiTietKiem;
    }

    public void setNgayGuiTietKiem(String ngayGuiTietKiem) {
        this.ngayGuiTietKiem = ngayGuiTietKiem;
    }

    public double getLaiSuat() {
        return laiSuat;
    }

    public void setLaiSuat(double laiSuat) {
        this.laiSuat = laiSuat;
    }

    public int getKyHan() {
        return kyHan;
    }

    public void setKyHan(int kyHan) {
        this.kyHan = kyHan;
    }

    /**
     * OVERRIDE ham layThongTinRieng() cua lop cha (TaiKhoanNganHang).
     * Day la noi xu ly "nghiep vu rieng" cua tai khoan tiet kiem: tra ve chuoi
     * hien thi cac thong tin ma CHI tai khoan tiet kiem moi co.
     */
    @Override
    public String layThongTinRieng() {
        return "Loai tai khoan    : Tiet kiem\n" +
                "So tien gui TK    : " + formatSo(soTienGuiTietKiem) + "\n" +
                "Ngay gui tiet kiem: " + ngayGuiTietKiem + "\n" +
                "Lai suat (%)      : " + formatSo(laiSuat) + "\n" +
                "Ky han (thang)    : " + kyHan;
    }

    /**
     * OVERRIDE ham toCsvLine() cua lop cha.
     * Tai khoan tiet kiem co 8 cot du lieu trong file CSV, theo dung thu tu:
     * id, maTaiKhoan, tenChuTaiKhoan, ngayTaoTaiKhoan,
     * soTienGuiTietKiem, ngayGuiTietKiem, laiSuat, kyHan
     *
     * Dung formatSo(...) thay vi noi chuoi truc tiep de tranh so bi ghi ra file
     * duoi dang khoa hoc (vi du "1.5E7") khien file CSV kho doc / sai dinh dang.
     */
    @Override
    public String toCsvLine() {
        return id + "," + maTaiKhoan + "," + tenChuTaiKhoan + "," + ngayTaoTaiKhoan + "," +
                formatSo(soTienGuiTietKiem) + "," + ngayGuiTietKiem + "," + formatSo(laiSuat) + "," + kyHan;
    }
}
