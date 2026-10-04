package com.codegym.bank;

import com.codegym.bank.exception.NotFoundBankAccountException;
import com.codegym.bank.model.TaiKhoanNganHang;
import com.codegym.bank.model.TaiKhoanTietKiem;
import com.codegym.bank.model.TaiKhoanThanhToan;
import com.codegym.bank.service.BankAccountService;

import java.util.List;
import java.util.Scanner;

/**
 * CLASS CHINH (Main) cua chuong trinh "Quan ly tai khoan ngan hang".
 *
 * Nhiem vu cua class nay CHI la:
 * - Hien thi menu.
 * - Nhan du lieu nguoi dung nhap tu ban phim (va validate du lieu dau vao).
 * - Goi xuong BankAccountService de xu ly nghiep vu that su (doc/ghi file, them/xoa/tim...).
 *
 * Cach to chuc nay giup tach biet "giao dien console" (Main) ra khoi "logic nghiep vu"
 * (BankAccountService), la mot thoi quen lap trinh tot (clean code).
 */
public class Main {

    // Scanner dung CHUNG cho ca chuong trinh de doc du lieu tu ban phim (System.in).
    // Chi tao MOT lan duy nhat (khong tao moi Scanner() nhieu lan) de tranh loi/ro ri tai nguyen.
    private static final Scanner scanner = new Scanner(System.in);

    // Doi tuong Service, noi chua toan bo logic doc/ghi file CSV va xu ly nghiep vu.
    private static final BankAccountService service = new BankAccountService();

    public static void main(String[] args) {
        boolean dangChay = true;

        // Vong lap chinh: hien thi menu -> doc lua chon -> xu ly -> lap lai,
        // cho den khi nguoi dung chon "5. Thoat" thi dangChay = false va ket thuc chuong trinh.
        while (dangChay) {
            hienThiMenu();
            int luaChon = nhapSoNguyen("Chon chuc nang: ");

            switch (luaChon) {
                case 1:
                    chucNangThemMoi();
                    break;
                case 2:
                    chucNangXoa();
                    break;
                case 3:
                    chucNangXemDanhSach();
                    break;
                case 4:
                    chucNangTimKiem();
                    break;
                case 5:
                    dangChay = false;
                    System.out.println("Cam on ban da su dung chuong trinh. Tam biet!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le, vui long chon lai tu 1 den 5.");
            }
        }

        scanner.close();
    }

    // ============================================================
    // ===================== HIEN THI MENU =======================
    // ============================================================

    private static void hienThiMenu() {
        System.out.println();
        System.out.println("--CHUONG TRINH QUAN LY TAI KHOAN NGAN HANG--");
        System.out.println("Chon chuc nang theo so (de tiep tuc):");
        System.out.println("1. Them moi");
        System.out.println("2. Xoa");
        System.out.println("3. Xem danh sach cac tai khoan ngan hang");
        System.out.println("4. Tim kiem");
        System.out.println("5. Thoat");
    }

    // ============================================================
    // ================= 1. CHUC NANG THEM MOI ====================
    // ============================================================

    private static void chucNangThemMoi() {
        System.out.println();
        System.out.println("--- THEM MOI TAI KHOAN NGAN HANG ---");

        // Cac thong tin DUNG CHUNG, bat buoc phai nhap (khong duoc de trong).
        String maTaiKhoan = nhapChuoiBatBuoc("Ma tai khoan: ");
        String tenChuTaiKhoan = nhapChuoiBatBuoc("Ten chu tai khoan: ");
        String ngayTaoTaiKhoan = nhapChuoiBatBuoc("Ngay tao tai khoan (dd/MM/yyyy): ");

        // Id duoc TU DONG sinh ra boi Service, nguoi dung KHONG duoc tu nhap Id.
        int idMoi = service.sinhIdTiepTheo();

        // Hoi nguoi dung muon tao loai tai khoan nao, de biet nhap tiep thong tin rieng gi.
        int loaiTaiKhoan = nhapLoaiTaiKhoan();

        TaiKhoanNganHang taiKhoanMoi;
        if (loaiTaiKhoan == 1) {
            // ----- Tai khoan TIET KIEM: can them 4 thong tin rieng -----
            double soTienGuiTietKiem = nhapSoThucDuong("So tien gui tiet kiem: ");
            String ngayGuiTietKiem = nhapChuoiBatBuoc("Ngay gui tiet kiem (dd/MM/yyyy): ");
            double laiSuat = nhapSoThucDuong("Lai suat (%): ");
            int kyHan = nhapSoNguyenDuong("Ky han (so thang): ");

            taiKhoanMoi = new TaiKhoanTietKiem(idMoi, maTaiKhoan, tenChuTaiKhoan, ngayTaoTaiKhoan,
                    soTienGuiTietKiem, ngayGuiTietKiem, laiSuat, kyHan);
        } else {
            // ----- Tai khoan THANH TOAN: can them 2 thong tin rieng -----
            String soThe = nhapSoTheHopLe("So the: ");
            double soTienTrongTaiKhoan = nhapSoThucDuong("So tien trong tai khoan: ");

            taiKhoanMoi = new TaiKhoanThanhToan(idMoi, maTaiKhoan, tenChuTaiKhoan, ngayTaoTaiKhoan,
                    soThe, soTienTrongTaiKhoan);
        }

        // Toi day, moi du lieu da duoc validate NGAY TU LUC NHAP (xem cac ham nhapXxx() ben duoi),
        // nen co the yen tam ghi thang xuong file CSV.
        service.themTaiKhoan(taiKhoanMoi);
        System.out.println("Them moi tai khoan thanh cong! Id tai khoan moi duoc gan la: " + idMoi);
    }

    /** Hoi nguoi dung chon loai tai khoan: 1 = Tiet kiem, 2 = Thanh toan. Lap lai cho den khi nhap dung. */
    private static int nhapLoaiTaiKhoan() {
        while (true) {
            System.out.println("Chon loai tai khoan:");
            System.out.println("  1. Tai khoan tiet kiem");
            System.out.println("  2. Tai khoan thanh toan");
            int luaChon = nhapSoNguyen("Nhap lua chon (1 hoac 2): ");
            if (luaChon == 1 || luaChon == 2) {
                return luaChon;
            }
            System.out.println("Lua chon khong hop le, vui long nhap lai.");
        }
    }

    // ============================================================
    // =================== 2. CHUC NANG XOA =======================
    // ============================================================

    private static void chucNangXoa() {
        System.out.println();
        System.out.println("--- XOA TAI KHOAN NGAN HANG ---");

        String maTaiKhoan = nhapChuoiBatBuoc("Nhap ma tai khoan can xoa: ");

        // Dung vong lap de xu ly truong hop nguoi dung nhap sai ma tai khoan nhieu lan.
        while (true) {
            try {
                // Neu ma tai khoan KHONG ton tai trong danh sach -> nem (throw) ra
                // Exception tu dinh nghia NotFoundBankAccountException.
                if (!service.tonTaiMaTaiKhoan(maTaiKhoan)) {
                    throw new NotFoundBankAccountException("Tai khoan khong ton tai.");
                }

                // Ma tai khoan ton tai -> CANH BAO va yeu cau xac nhan truoc khi xoa that su.
                boolean dongY = nhapXacNhanYesNo(
                        "Ban co chac chan muon xoa tai khoan \"" + maTaiKhoan + "\" khong? (Yes/No): ");

                if (dongY) {
                    List<TaiKhoanNganHang> danhSachSauKhiXoa = service.xoaTaiKhoanTheoMa(maTaiKhoan);
                    System.out.println("Xoa tai khoan thanh cong!");
                    System.out.println("Danh sach sau khi xoa:");
                    hienThiDanhSach(danhSachSauKhiXoa);
                } else {
                    System.out.println("Da huy thao tac xoa. Quay ve menu chinh.");
                }
                return; // da xu ly xong (du la xoa hay huy) -> thoat khoi chuc nang nay

            } catch (NotFoundBankAccountException e) {
                // In ra chinh thong diep da duoc gan trong Exception (xem getMessage()).
                System.out.println("Loi: " + e.getMessage());
                System.out.print("Nhap lai ma tai khoan can xoa (hoac nhan Enter de quay ve menu chinh): ");
                String nhapLai = scanner.nextLine().trim();

                if (nhapLai.isEmpty()) {
                    return; // nguoi dung nhan Enter (khong nhap gi) -> quay ve menu chinh
                }
                // Nguoc lai, coi chuoi vua nhap la ma tai khoan moi va thu lai vong lap.
                maTaiKhoan = nhapLai;
            }
        }
    }

    // ============================================================
    // ============== 3. CHUC NANG XEM DANH SACH ===================
    // ============================================================

    private static void chucNangXemDanhSach() {
        System.out.println();
        System.out.println("--- DANH SACH TAI KHOAN NGAN HANG ---");
        List<TaiKhoanNganHang> danhSach = service.xemDanhSach();
        hienThiDanhSach(danhSach);
    }

    // ============================================================
    // ================ 4. CHUC NANG TIM KIEM ======================
    // ============================================================

    private static void chucNangTimKiem() {
        System.out.println();
        System.out.println("--- TIM KIEM TAI KHOAN NGAN HANG ---");
        String tuKhoa = nhapChuoiBatBuoc("Nhap tu khoa (ma tai khoan hoac ten chu tai khoan): ");

        List<TaiKhoanNganHang> ketQua = service.timKiem(tuKhoa);
        if (ketQua.isEmpty()) {
            System.out.println("Khong tim thay tai khoan nao phu hop voi tu khoa \"" + tuKhoa + "\".");
        } else {
            System.out.println("Tim thay " + ketQua.size() + " tai khoan phu hop:");
            hienThiDanhSach(ketQua);
        }
    }

    // ============================================================
    // ============= HAM DUNG CHUNG DE HIEN THI DANH SACH ===========
    // ============================================================

    /**
     * In ra danh sach tai khoan ra man hinh Console.
     * Voi moi tai khoan, chi can goi System.out.println(taiKhoan) la Java se TU DONG
     * goi toi phuong thuc toString() cua doi tuong do -> nho tinh da hinh (polymorphism),
     * no se hien thi dung thong tin rieng cua Tiet kiem hay Thanh toan ma khong can
     * phai viet code kiem tra "if (taiKhoan instanceof ...)" o day.
     */
    private static void hienThiDanhSach(List<TaiKhoanNganHang> danhSach) {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sach rong, hien chua co tai khoan nao.");
            return;
        }
        for (TaiKhoanNganHang taiKhoan : danhSach) {
            System.out.println("----------------------------------------");
            System.out.println(taiKhoan);
        }
        System.out.println("----------------------------------------");
    }

    // ============================================================
    // ========= CAC HAM HO TRO NHAP & VALIDATE DU LIEU ============
    // ============================================================
    // Y tuong chung: moi ham se LAP LAI viec hoi nguoi dung cho den khi
    // nhan duoc du lieu HOP LE, thay vi lam gian doan/thoat chuong trinh khi nhap sai.

    /** Doc 1 chuoi, BAT BUOC khac rong. Neu nguoi dung bo trong thi bat nhap lai. */
    private static String nhapChuoiBatBuoc(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String giaTri = scanner.nextLine().trim();
            if (!giaTri.isEmpty()) {
                return giaTri;
            }
            System.out.println("Loi: Truong nay khong duoc de trong. Vui long nhap lai.");
        }
    }

    /** Doc 1 so nguyen bat ky (dung cho cac lua chon menu). */
    private static int nhapSoNguyen(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String giaTri = scanner.nextLine().trim();
            try {
                return Integer.parseInt(giaTri);
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap mot so nguyen hop le.");
            }
        }
    }

    /** Doc 1 so nguyen BAT BUOC phai duong (> 0). Dung cho truong "Ky han". */
    private static int nhapSoNguyenDuong(String thongBao) {
        while (true) {
            int giaTri = nhapSoNguyen(thongBao);
            if (giaTri > 0) {
                return giaTri;
            }
            System.out.println("Loi: Gia tri phai la so nguyen duong (lon hon 0). Vui long nhap lai.");
        }
    }

    /** Doc 1 so thuc BAT BUOC phai duong (> 0). Dung cho So tien gui TK, Lai suat, So tien trong TK... */
    private static double nhapSoThucDuong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String giaTri = scanner.nextLine().trim();
            try {
                double so = Double.parseDouble(giaTri);
                if (so > 0) {
                    return so;
                }
                System.out.println("Loi: Gia tri phai la so duong (lon hon 0). Vui long nhap lai.");
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap mot so hop le.");
            }
        }
    }

    /**
     * Doc "So the": bat buoc khong duoc trong, chi duoc chua chu so, va gia tri phai la so duong.
     * Ket qua van duoc TRA VE duoi dang chuoi (String) de giu nguyen dung tung chu so,
     * tranh truong hop so the qua lon lam tran kieu so hoac mat so 0 o dau.
     */
    private static String nhapSoTheHopLe(String thongBao) {
        while (true) {
            String soThe = nhapChuoiBatBuoc(thongBao);
            try {
                long giaTri = Long.parseLong(soThe);
                if (giaTri > 0) {
                    return soThe;
                }
                System.out.println("Loi: So the phai la so duong. Vui long nhap lai.");
            } catch (NumberFormatException e) {
                System.out.println("Loi: So the chi duoc chua chu so (0-9). Vui long nhap lai.");
            }
        }
    }

    /** Doc xac nhan Yes/No tu nguoi dung. Tra ve true neu Yes, false neu No. */
    private static boolean nhapXacNhanYesNo(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String giaTri = scanner.nextLine().trim();
            if (giaTri.equalsIgnoreCase("yes") || giaTri.equalsIgnoreCase("y")) {
                return true;
            }
            if (giaTri.equalsIgnoreCase("no") || giaTri.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Vui long nhap \"Yes\" hoac \"No\".");
        }
    }
}
