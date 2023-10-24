package rating

import "context"

type Config struct {
	Host     string
	Port     string
	MaxFails uint64
}

type Client interface {
	GetUserRating(ctx context.Context, username string) (Rating, error)
	UpdateUserRating(ctx context.Context, username string, diff int) error
}
