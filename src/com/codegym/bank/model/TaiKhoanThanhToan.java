package com.codegym.bank.model;

/**
 * Lop TaiKhoanThanhToan (Tai khoan thanh toan).
 *
 * Giong nhu TaiKhoanTietKiem, lop nay cung "extends TaiKhoanNganHang" de ke thua
 * cac thuoc tinh/phuong thuc dung chung, va chi khai bao THEM phan rieng cua no.
 */
public class TaiKhoanThanhToan extends TaiKhoanNganHang {

    // ===================== THUOC TINH RIENG CUA TAI KHOAN THANH TOAN =====================
    // Luu soThe dang String (chuoi) thay vi so, de:
    // - Khong bi mat so 0 o dau so the (neu co).
    // - Khong bi gioi han / sai lech do so the thuong rat dai (vuot qua int, gan voi long).
    private String soThe;
    private double soTienTrongTaiKhoan; // So tien hien co trong tai khoan thanh toan

    public TaiKhoanThanhToan(int id, String maTaiKhoan, String tenChuTaiKhoan, String ngayTaoTaiKhoan,
                              String soThe, double soTienTrongTaiKhoan) {
        super(id, maTaiKhoan, tenChuTaiKhoan, ngayTaoTaiKhoan);
        this.soThe = soThe;
        this.soTienTrongTaiKhoan = soTienTrongTaiKhoan;
    }

    // ===================== GETTER / SETTER RIENG =====================

    public String getSoThe() {
        return soThe;
    }

    public void setSoThe(String soThe) {
        this.soThe = soThe;
    }

    public double getSoTienTrongTaiKhoan() {
        return soTienTrongTaiKhoan;
    }

    public void setSoTienTrongTaiKhoan(double soTienTrongTaiKhoan) {
        this.soTienTrongTaiKhoan = soTienTrongTaiKhoan;
    }

    /**
     * OVERRIDE ham layThongTinRieng(): tra ve thong tin CHI tai khoan thanh toan moi co.
     */
    @Override
    public String layThongTinRieng() {
        return "Loai tai khoan    : Thanh toan\n" +
                "So the            : " + soThe + "\n" +
                "So tien trong TK  : " + formatSo(soTienTrongTaiKhoan);
    }

    /**
     * OVERRIDE ham toCsvLine(): tai khoan thanh toan co 6 cot du lieu, theo thu tu:
     * id, maTaiKhoan, tenChuTaiKhoan, ngayTaoTaiKhoan, soThe, soTienTrongTaiKhoan
     *
     * Dung formatSo(...) de tranh so tien bi ghi ra file duoi dang khoa hoc (vi du "1.5E7").
     */
    @Override
    public String toCsvLine() {
        return id + "," + maTaiKhoan + "," + tenChuTaiKhoan + "," + ngayTaoTaiKhoan + "," +
                soThe + "," + formatSo(soTienTrongTaiKhoan);
    }
}
