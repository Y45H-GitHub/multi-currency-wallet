# Multi-Currency Wallet API - Render Deployment Guide

## Prerequisites
1. GitHub account with your code pushed to a repository
2. Render account (free tier available)

## Deployment Steps

### Option 1: Using render.yaml (Recommended)
1. Push your code to GitHub
2. Go to [Render Dashboard](https://dashboard.render.com/)
3. Click "New" → "Blueprint"
4. Connect your GitHub repository
5. Render will automatically detect `render.yaml` and create:
   - Web Service (your Spring Boot API)
   - PostgreSQL Database (free tier)

### Option 2: Manual Setup
1. **Create Database:**
   - Go to Render Dashboard
   - Click "New" → "PostgreSQL"
   - Name: `multi-currency-wallet-db`
   - Plan: Free
   - Note down the connection details

2. **Create Web Service:**
   - Click "New" → "Web Service"
   - Connect your GitHub repository
   - Runtime: Docker
   - Build Command: (leave empty - Docker handles it)
   - Start Command: (leave empty - Docker handles it)

3. **Set Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=render
   DATABASE_URL=<your-database-url-from-step-1>
   PORT=8080
   ```

## Environment Variables Needed
- `SPRING_PROFILES_ACTIVE=render`
- `DATABASE_URL` (automatically provided by Render database)
- `PORT` (automatically set by Render)

## Health Check
Render will use `/actuator/health` to check if your app is running.

## API Endpoints
Once deployed, your API will be available at:
- `https://your-app-name.onrender.com/api/wallets`
- `https://your-app-name.onrender.com/actuator/health`

## Testing Deployment
```bash
# Health check
curl https://your-app-name.onrender.com/actuator/health

# Create wallet
curl -X POST https://your-app-name.onrender.com/api/wallets \
  -H "Content-Type: application/json" \
  -d '{"userId": "123e4567-e89b-12d3-a456-426614174000"}'
```

## Important Notes
- Free tier has limitations (sleeps after 15 minutes of inactivity)
- Database has 1GB storage limit on free tier
- First request after sleep may take 30+ seconds to wake up
- Consider upgrading to paid plans for production use