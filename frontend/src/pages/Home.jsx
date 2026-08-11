import { useEffect, useState } from "react";
import { Box, Button, Container, Typography } from "@mui/material";
import { checkBackendHealth } from "../services/healthService";

function Home() {

    const [backendStatus, setBackendStatus] = useState("Checking...");

    useEffect(() => {

        const checkHealth = async () => {

            try {

                const data = await checkBackendHealth();

                setBackendStatus(data.message);

            } catch (error) {

                console.error("Backend connection failed:", error);

                setBackendStatus("Backend connection failed");
            }
        };

        checkHealth();

    }, []);

    return (
        <Box
            sx={{
                minHeight: "100vh",
                background: "#f7e6e6",
                display: "flex",
                alignItems: "center",
            }}
        >
            <Container maxWidth="md">

                <Typography
                    variant="h2"
                    component="h1"
                    fontWeight="bold"
                    gutterBottom
                >
                    Awareness of the Indian Constitution
                </Typography>

                <Typography
                    variant="h6"
                    color="text.secondary"
                    sx={{ mb: 4 }}
                >
                    Explore, understand, and learn about the Constitution
                    through interactive educational content.
                </Typography>

                <Button
                    variant="contained"
                    size="large"
                    sx={{
                        backgroundColor: "#8b0000",
                        "&:hover": {
                            backgroundColor: "#650000",
                        },
                    }}
                >
                    Explore Constitution
                </Button>

                <Typography
                    sx={{
                        mt: 4,
                        fontWeight: "bold",
                    }}
                >
                    Backend Status: {backendStatus}
                </Typography>

            </Container>
        </Box>
    );
}

export default Home;