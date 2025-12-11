# FuzzyService

## 📈 Deployment Tracking

This repository includes automated deployment tracking using New Relic. Deployments are automatically recorded when code is pushed to the `master` branch.

### Required GitHub Secrets

To enable deployment tracking, configure the following repository secrets:

1. **NEW_RELIC_API_KEY**: Your New Relic User API key
   - [How to get your API key](https://docs.newrelic.com/docs/apis/intro-apis/new-relic-api-keys/)
   
2. **NEW_RELIC_ENTITY_GUID**: Your APM application's entity GUID
   - Find this in New Relic One: Go to your application → Click "See metadata and tags" → Copy the GUID
   - [Learn more about entity GUIDs](https://docs.newrelic.com/docs/new-relic-one/use-new-relic-one/core-concepts/what-entity-new-relic/)

### Setting Up Secrets

1. Go to your repository on GitHub
2. Navigate to **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Add both `NEW_RELIC_API_KEY` and `NEW_RELIC_ENTITY_GUID`

### How It Works

The deployment tracking is configured in `.github/workflows/dependency-submission.yml` and runs automatically on every push to the `master` branch. Each deployment is tagged with the commit SHA for traceability.

For more information, see:
- [New Relic Deployment Tracking Documentation](https://docs.newrelic.com/docs/apm/new-relic-apm/maintenance/record-monitor-deployments/)
- [GitHub Actions Deployment Marker](https://github.com/marketplace/actions/new-relic-deployment-marker)