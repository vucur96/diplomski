package etf.bg.diplomski.user;

public record ChangePassDTO(
        String username,
        String newPassword,

        String oldPassword
) {}
