package controller.fine;

import DB.DBConnection;
import model.Fine;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FineController {

    private static final double FINE_RATE_PER_DAY = 2;

    // Create a new fine record
    public boolean createFine(Fine fine) {
        String sql = "INSERT INTO fine (amount, date_count, book_borrow_id, admin_id) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDouble(1, fine.getAmount());
            pstmt.setString(2, fine.getDate_count());
            pstmt.setString(3, fine.getBookBorrowId());
            pstmt.setInt(4, fine.getAdminId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        fine.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update an existing fine record
    public boolean updateFine(Fine fine) {
        String sql = "UPDATE fine SET amount = ?, date_count = ?, admin_id = ? " +
                "WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, fine.getAmount());
            pstmt.setString(2, fine.getDate_count());
            pstmt.setInt(3, fine.getAdminId());
            pstmt.setInt(4, fine.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a fine record
    public boolean deleteFine(int fineId) {
        String sql = "DELETE FROM fine WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, fineId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get a single fine by ID
    public Fine getFineById(int fineId) {
        String sql = "SELECT * FROM fine WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, fineId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractFineFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all fines for a specific member
    public List<Fine> getFinesByMemberId(String memberId) {
        String sql = "SELECT f.* FROM fine f " +
                "JOIN book_borrow bb ON f.book_borrow_id = bb.id " +
                "WHERE bb.member_id = ?";

        List<Fine> fines = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    fines.add(extractFineFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fines;
    }

    // Get all fines (can be used for TableView)
    public List<Fine> getAllFines() {
        String sql = "SELECT * FROM fine";
        List<Fine> fines = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                fines.add(extractFineFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fines;
    }

    // Get total fine amount for a member
    public double getTotalFineAmount(String memberId) {
        String sql = "SELECT SUM(f.amount) as total_fine FROM fine f " +
                "JOIN book_borrow bb ON f.book_borrow_id = bb.id " +
                "WHERE bb.member_id = ? AND f.amount > 0";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println(rs.getString("total_fine"));
                    return rs.getDouble("total_fine");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Calculate fine for a specific borrow
    public double calculateFine(String borrowId) {
        String sql = "SELECT borrowed_date, returnDate, returnedDate, isReturned FROM book_borrow WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, borrowId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Date returnDate = rs.getDate("returnDate");
                    Date returnedDate = rs.getDate("returnedDate");
                    boolean isReturned = rs.getBoolean("isReturned");

                    // If book is not returned yet, calculate based on current date
                    if (!isReturned) {
                        returnedDate = Date.valueOf(LocalDate.now());
                    }

                    // If the book was returned after the due date
                    if (returnedDate != null && returnDate != null && returnedDate.after(returnDate)) {
                        LocalDate dueDate = returnDate.toLocalDate();
                        LocalDate actualReturnDate = returnedDate.toLocalDate();
                        long daysLate = ChronoUnit.DAYS.between(dueDate, actualReturnDate);
                        // Ensure we return at least 0 (no negative fines)
                        return Math.max(0, daysLate * FINE_RATE_PER_DAY);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Get late borrows with fine details
    public List<FineDetail> getLateBookDetails(String memberId) {
        // Corrected SQL query - verify the actual column name for book title in your database
        // Based on the ER diagram, it should be 'name' in the book table
        String sql = "SELECT bb.id as borrow_id, b.id as book_id, b.name as book_title, bb.borrowed_date, bb.returnDate, " +
                "CASE WHEN bb.isReturned = 0 THEN DATEDIFF(CURRENT_DATE, bb.returnDate) " +
                "ELSE DATEDIFF(bb.returnedDate, bb.returnDate) END AS days_late, " +
                "f.id as fine_id, f.amount " +
                "FROM book_borrow bb " +
                "JOIN book b ON bb.book_id = b.id " +
                "LEFT JOIN fine f ON bb.id = f.book_borrow_id " +
                "WHERE bb.member_id = ? AND " +
                "((bb.isReturned = 0 AND CURRENT_DATE > bb.returnDate) OR " +
                "(bb.isReturned = 1 AND bb.returnedDate > bb.returnDate))";

        List<FineDetail> results = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FineDetail detail = new FineDetail();
                    detail.setBorrowId(rs.getString("borrow_id"));
                    detail.setBookId(rs.getString("book_id"));
                    detail.setBookTitle(rs.getString("book_title"));
                    detail.setDueDate(rs.getDate("returnDate"));

                    // Ensure days late is not negative
                    int daysLate = Math.max(0, rs.getInt("days_late"));
                    detail.setDaysLate(daysLate);

                    // If fine record exists
                    if (rs.getObject("fine_id") != null) {
                        detail.setFineId(rs.getInt("fine_id"));
                        // Use the amount from database if it exists
                        double amount = rs.getDouble("amount");
                        // If amount is 0 (paid) but we're still showing it, recalculate for display
                        detail.setFineAmount(amount > 0 ? amount : daysLate * FINE_RATE_PER_DAY);
                    } else {
                        detail.setFineId(0); // Indicate fine not yet created
                        detail.setFineAmount(daysLate * FINE_RATE_PER_DAY);
                    }

                    results.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Get counts of books out and books late for a member
    public int[] getMemberBorrowStats(String memberId) {
        String sql = "SELECT " +
                "SUM(CASE WHEN isReturned = 0 THEN 1 ELSE 0 END) AS books_out, " +
                "SUM(CASE WHEN isReturned = 0 AND CURRENT_DATE > returnDate THEN 1 ELSE 0 END) AS books_late " +
                "FROM book_borrow WHERE member_id = ?";

        int[] stats = new int[2]; // [booksOut, booksLate]

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    stats[0] = rs.getInt("books_out");
                    stats[1] = rs.getInt("books_late");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    // Record payment for fines
    public boolean recordFinePayment(List<Integer> fineIds, int adminId) {
        if (fineIds.isEmpty()) {
            return false;
        }

        String sql = "UPDATE fine SET amount = 0, admin_id = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Integer fineId : fineIds) {
                    pstmt.setInt(1, adminId);
                    pstmt.setInt(2, fineId);
                    pstmt.addBatch();
                }

                int[] results = pstmt.executeBatch();
                conn.commit();

                // Check if all updates were successful
                for (int result : results) {
                    if (result <= 0) {
                        return false;
                    }
                }
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper class for fine details
    public static class FineDetail {
        private String borrowId;
        private String bookId;
        private String bookTitle;
        private Date dueDate;
        private int daysLate;
        private double fineAmount;
        private int fineId;

        // Getters and setters
        public String getBorrowId() {
            return borrowId;
        }

        public void setBorrowId(String borrowId) {
            this.borrowId = borrowId;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public void setBookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }

        public Date getDueDate() {
            return dueDate;
        }

        public void setDueDate(Date dueDate) {
            this.dueDate = dueDate;
        }

        public int getDaysLate() {
            return daysLate;
        }

        public void setDaysLate(int daysLate) {
            this.daysLate = daysLate;
        }

        public double getFineAmount() {
            return fineAmount;
        }

        public void setFineAmount(double fineAmount) {
            this.fineAmount = fineAmount;
        }

        public int getFineId() {
            return fineId;
        }

        public void setFineId(int fineId) {
            this.fineId = fineId;
        }
    }

    // Helper method to extract fine from ResultSet
    private Fine extractFineFromResultSet(ResultSet rs) throws SQLException {
        Fine fine = new Fine();
        fine.setId(rs.getInt("id"));
        fine.setAmount(rs.getDouble("amount"));
        fine.setDate_count(rs.getString("date_count"));
        fine.setBookBorrowId(rs.getString("book_borrow_id"));
        fine.setAdminId(rs.getInt("admin_id"));
        return fine;
    }
}