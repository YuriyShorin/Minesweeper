CREATE TABLE IF NOT EXISTS Games(
    game_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    width INTEGER NOT NULL,
    height INTEGER NOT NULL,
    mines_count INTEGER NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    field TEXT NOT NULL
);