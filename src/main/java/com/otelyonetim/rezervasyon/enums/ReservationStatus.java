package com.otelyonetim.rezervasyon.enums;

public enum ReservationStatus {
    PENDING,        // Beklemede (ödeme yapılmadı)
    CONFIRMED,      // Onaylandı (ödeme yapıldı)
    CANCELLED,      // İptal edildi
    CHECKED_IN,     // Giriş yapıldı
    CHECKED_OUT,    // Çıkış yapıldı
    NOSHOW,         // Gelmedi
    MODIFIED        // Değiştirildi
}
