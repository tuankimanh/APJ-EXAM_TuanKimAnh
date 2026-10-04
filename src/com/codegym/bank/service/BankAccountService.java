package com.codegym.bank.service;

import com.codegym.bank.exception.NotFoundBankAccountException;
import com.codegym.bank.model.TaiKhoanNganHang;
import com.codegym.bank.model.TaiKhoanTietKiem;
import com.codegym.bank.model.TaiKhoanThanhToan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Lop Service: noi tap trung TOAN BO logic doc/ghi file CSV va cac nghiep vu
 * lien quan den danh sach tai khoan ngan hang (them, xoa, xem, tim kiem).
 *
 * Tach rieng phan nay ra khoi Main (noi chi lo hien thi menu va nhan input)
 * giup code ro rang, de doc, de bao tri hon (dung nguyen tac "Single Responsibility"
 * - moi class chi lam mot nhiem vu chinh).
 */
public class BankAccountService {

    // Duong dan toi file du lieu, dung theo yeu cau de bai: "data/bank_accounts.csv"
    // Day la duong dan TUONG DOI, tinh tu thu muc goc cua project khi chay chuong trinh.
    private static final String FILE_PATH = "data/bank_accounts.csv";

    /**
     * Doc toan bo du lieu tu file CSV va chuyen thanh danh sach doi tuong TaiKhoanNganHang
     * (thuc chat moi phan tu la TaiKhoanTietKiem hoac TaiKhoanThanhToan).
     *
     * Quy uoc theo de bai (xem file mau bank_accounts.csv):
     * - Dong co 8 gia tri (cot)  -> Tai khoan TIET KIEM
     * - Dong co 6 gia tri (cot)  -> Tai khoan THANH TOAN
     */
    public List<TaiKhoanNganHang> docDanhSachTuFile() {
        List<TaiKhoanNganHang> danhSach = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Neu file chua ton tai (lan dau chay chuong trinh, chua co du lieu nao)
        // thi tra ve danh sach rong, khong bao loi.
        if (!file.exists()) {
            return danhSach;
        }

        // try-with-resources: BufferedReader se TU DONG duoc dong lai (close)
        // sau khi ra khoi khoi try, kho can viet finally de dong file thu cong.
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String dong;
            // Doc tung dong trong file cho den khi het file (readLine() tra ve null)
            while ((dong = reader.readLine()) != null) {
                dong = dong.trim();
                if (dong.isEmpty()) {
                    continue; // bo qua dong trong (neu co)
                }

                // Tach dong CSV thanh mang cac gia tri, phan cach boi dau phay ","
                // Tham so "-1" giup giu lai ca cac o rong o CUOI dong (neu co)
                String[] cot = dong.split(",", -1);
                for (int i = 0; i < cot.length; i++) {
                    cot[i] = cot[i].trim(); // xoa khoang trang thua quanh moi gia tri
                }

                TaiKhoanNganHang taiKhoan = chuyenDongCsvThanhDoiTuong(cot);
                if (taiKhoan != null) {
                    danhSach.add(taiKhoan);
                }
            }
        } catch (IOException e) {
            System.out.println("Loi khi doc file du lieu: " + e.getMessage());
        }

        return danhSach;
    }

    /**
     * Chuyen 1 mang chuoi (1 dong CSV da duoc tach cot) thanh doi tuong tai khoan phu hop.
     * Dua vao SO LUONG COT de biet day la tai khoan tiet kiem (8 cot) hay thanh toan (6 cot).
     * Neu du lieu sai dinh dang thi bo qua dong do (khong lam crash ca chuong trinh).
     */
    private TaiKhoanNganHang chuyenDongCsvThanhDoiTuong(String[] cot) {
        try {
            int id = Integer.parseInt(cot[0]);
            String maTaiKhoan = cot[1];
            String tenChuTaiKhoan = cot[2];
            String ngayTaoTaiKhoan = cot[3];

            if (cot.length == 8) {
                // Tai khoan TIET KIEM:
                // id,maTaiKhoan,tenChuTaiKhoan,ngayTaoTaiKhoan,soTienGuiTietKiem,ngayGuiTietKiem,laiSuat,kyHan
                double soTienGuiTietKiem = Double.parseDouble(cot[4]);
                String ngayGuiTietKiem = cot[5];
                double laiSuat = Double.parseDouble(cot[6]);
                int kyHan = Integer.parseInt(cot[7]);
                return new TaiKhoanTietKiem(id, maTaiKhoan, tenChuTaiKhoan, ngayTaoTaiKhoan,
                        soTienGuiTietKiem, ngayGuiTietKiem, laiSuat, kyHan);

            } else if (cot.length == 6) {
                // Tai khoan THANH TOAN:
                // id,maTaiKhoan,tenChuTaiKhoan,ngayTaoTaiKhoan,soThe,soTienTrongTaiKhoan
                String soThe = cot[4];
                double soTienTrongTaiKhoan = Double.parseDouble(cot[5]);
                return new TaiKhoanThanhToan(id, maTaiKhoan, tenChuTaiKhoan, ngayTaoTaiKhoan,
                        soThe, soTienTrongTaiKhoan);

            } else {
                // So cot khong phai 6 hay 8 -> du lieu khong hop le, bo qua dong nay
                System.out.println("Canh bao: dong du lieu khong hop le, da bo qua -> "
                        + String.join(",", cot));
                return null;
            }
        } catch (NumberFormatException e) {
            // Vi du: cot id hoac so tien bi loi, khong the chuyen thanh so
            System.out.println("Canh bao: du lieu so khong hop le trong dong, da bo qua -> "
                    + String.join(",", cot));
            return null;
        }
    }

    /**
     * Ghi DE (ghi lai tu dau, overwrite) toan bo danh sach tai khoan xuong file CSV.
     *
     * Moi doi tuong TU BIET cach sinh ra dong CSV cua rieng minh thong qua toCsvLine()
     * -> day chinh la TINH DA HINH: voi cung mot loi goi "taiKhoan.toCsvLine()",
     * TaiKhoanTietKiem va TaiKhoanThanhToan se tra ve 2 dinh dang dong khac nhau.
     */
    private void ghiDanhSachVaoFile(List<TaiKhoanNganHang> danhSach) {
        File file = new File(FILE_PATH);

        // Dam bao thu muc cha ("data") da ton tai truoc khi ghi file,
        // neu khong se bi loi FileNotFoundException khi thu muc chua duoc tao.
        File thuMucCha = file.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
            thuMucCha.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            for (TaiKhoanNganHang taiKhoan : danhSach) {
                writer.write(taiKhoan.toCsvLine());
                writer.newLine(); // xuong dong sau moi tai khoan
            }
        } catch (IOException e) {
            System.out.println("Loi khi ghi file du lieu: " + e.getMessage());
        }
    }

    /**
     * Tinh Id tu dong tang cho tai khoan MOI = Id cua tai khoan CUOI CUNG trong danh sach + 1.
     * Neu danh sach dang rong (chua co tai khoan nao) thi Id dau tien se la 1,
     * dung theo yeu cau cua de bai.
     */
    public int sinhIdTiepTheo() {
        List<TaiKhoanNganHang> danhSach = docDanhSachTuFile();
        if (danhSach.isEmpty()) {
            return 1;
        }
        // Vi tai khoan moi luon duoc them vao CUOI danh sach, nen chi can
        // lay Id cua phan tu cuoi cung roi + 1.
        int idCuoiCung = danhSach.get(danhSach.size() - 1).getId();
        return idCuoiCung + 1;
    }

    /**
     * Them 1 tai khoan moi vao file CSV:
     * doc danh sach hien tai -> them tai khoan moi vao cuoi danh sach -> ghi lai toan bo.
     */
    public void themTaiKhoan(TaiKhoanNganHang taiKhoanMoi) {
        List<TaiKhoanNganHang> danhSach = docDanhSachTuFile();
        danhSach.add(taiKhoanMoi);
        ghiDanhSachVaoFile(danhSach);
    }

    /**
     * Kiem tra xem Ma tai khoan co ton tai trong danh sach hay khong.
     * Dung cho buoc "xac nhan truoc khi xoa" o chuc nang Xoa.
     */
    public boolean tonTaiMaTaiKhoan(String maTaiKhoan) {
        return timTheoMaTaiKhoan(docDanhSachTuFile(), maTaiKhoan) != null;
    }

    /**
     * Xoa 1 tai khoan dua theo Ma tai khoan duoc truyen vao.
     *
     * Neu KHONG tim thay ma tai khoan tuong ung trong danh sach, ham se THROW ra
     * NotFoundBankAccountException voi thong diep "Tai khoan khong ton tai."
     * (dung theo yeu cau de bai).
     *
     * @return danh sach MOI (sau khi da xoa) de Main co the hien thi lai cho nguoi dung.
     */
    public List<TaiKhoanNganHang> xoaTaiKhoanTheoMa(String maTaiKhoan) throws NotFoundBankAccountException {
        List<TaiKhoanNganHang> danhSach = docDanhSachTuFile();

        TaiKhoanNganHang taiKhoanCanXoa = timTheoMaTaiKhoan(danhSach, maTaiKhoan);
        if (taiKhoanCanXoa == null) {
            throw new NotFoundBankAccountException("Tai khoan khong ton tai.");
        }

        danhSach.remove(taiKhoanCanXoa);
        ghiDanhSachVaoFile(danhSach);
        return danhSach;
    }

    /**
     * Ham ho tro (helper) DUNG CHUNG: tim 1 tai khoan trong danh sach theo dung
     * Ma tai khoan (so khop CHINH XAC, khong phan biet hoa/thuong).
     * Tra ve null neu khong tim thay.
     */
    private TaiKhoanNganHang timTheoMaTaiKhoan(List<TaiKhoanNganHang> danhSach, String maTaiKhoan) {
        for (TaiKhoanNganHang taiKhoan : danhSach) {
            if (taiKhoan.getMaTaiKhoan().equalsIgnoreCase(maTaiKhoan)) {
                return taiKhoan;
            }
        }
        return null;
    }

    /**
     * Lay toan bo danh sach tai khoan dang co trong file CSV.
     * Dung cho chuc nang "3. Xem danh sach".
     */
    public List<TaiKhoanNganHang> xemDanhSach() {
        return docDanhSachTuFile();
    }

    /**
     * Tim kiem GAN DUNG (chi can CHUA tu khoa, khong phan biet hoa/thuong)
     * theo Ma tai khoan HOAC Ten chu tai khoan.
     * Dung cho chuc nang "4. Tim kiem".
     */
    public List<TaiKhoanNganHang> timKiem(String tuKhoa) {
        List<TaiKhoanNganHang> ketQua = new ArrayList<>();
        String tuKhoaThuongHoa = tuKhoa.toLowerCase();

        for (TaiKhoanNganHang taiKhoan : docDanhSachTuFile()) {
            boolean khopMaTaiKhoan = taiKhoan.getMaTaiKhoan().toLowerCase().contains(tuKhoaThuongHoa);
            boolean khopTenChu = taiKhoan.getTenChuTaiKhoan().toLowerCase().contains(tuKhoaThuongHoa);

            if (khopMaTaiKhoan || khopTenChu) {
                ketQua.add(taiKhoan);
            }
        }
        return ketQua;
    }
}
