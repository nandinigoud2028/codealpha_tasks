def chatbot():
    print("===== SIMPLE CHATBOT =====")
    print("Type 'bye' to exit the chatbot.")

    while True:
        user_input = input("You: ").lower()

        if user_input == "hello" or user_input == "hi":
            print("Bot: Hi! Nice to meet you.")

        elif user_input == "how are you":
            print("Bot: I'm fine, thanks!")

        elif user_input == "what is your name":
            print("Bot: My name is SimpleBot.")

        elif user_input == "what can you do":
            print("Bot: I can have a simple conversation with you.")

        elif user_input == "bye":
            print("Bot: Goodbye! Have a nice day!")
            break

        else:
            print("Bot: Sorry, I don't understand that.")


# Start the chatbot
chatbot()