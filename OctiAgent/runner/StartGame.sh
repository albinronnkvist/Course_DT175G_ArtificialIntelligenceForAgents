#!/bin/bash

# Start the octi-monitor
gnome-terminal --tab --title="Octi Monitor" -- bash -c '
echo "Starting Octi Monitor..."
java -jar octi-monitor-0.0.4.jar
' &

echo "Manually click 'Start Server' and 'New MultiGame' in the Octi Monitor."
read -p "Press Enter to continue..."

# Run the first agent
gnome-terminal --tab --title="Red Agent" -- bash -c '
echo "Running first agent..."
java -jar ../target/agent_ai25_alrn1700.jar -n Albin -c red
' &

# Run the second agent
gnome-terminal --tab --title="Black Agent" -- bash -c '
echo "Running second agent..."
java -jar random-playing-agent-0.0.3.jar -n Random -c black
' &

echo "Everything is running. Manually click 'Start Game' in the Octi Monitor GUI."

