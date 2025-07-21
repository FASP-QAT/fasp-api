# Database Migrations

This directory contains Flyway database migration files. For detailed information about working with Flyway in this project, please refer to our [Flyway Documentation](https://fasp-qat.github.io/qat-documentation/docs/developer/guides/flyway).

## Directory Contents
This folder should contain:
- Versioned migrations (`V{version}__{description}.sql`)
- Repeatable migrations (`R__{description}.sql`)

## Quick Reference
- Never modify existing migration files
- Always add new migrations with a higher version number
- Keep migrations small and focused
- Test migrations thoroughly before applying to production

For complete guidelines, commands, and troubleshooting, see the [documentation](https://fasp-qat.github.io/qat-documentation/docs/developer/guides/flyway).
