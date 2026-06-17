'use strict';

const path = require('path');
const protobuf = require('protobufjs');

// Load protobuf definitions from a trusted, static bundled .proto file.
// The definition path is resolved relative to this file at startup and is
// never influenced by external or user-controlled input.
const PROTO_PATH = path.resolve(__dirname, '..', 'proto', 'trace.proto');

let _root = null;

/**
 * Load and cache the protobuf root from the static definition file.
 * Throws if the file cannot be parsed.
 *
 * @returns {Promise<protobuf.Root>}
 */
async function getRoot() {
  if (!_root) {
    _root = await protobuf.load(PROTO_PATH);
  }
  return _root;
}

/**
 * Decode a binary-encoded ExportTraceServiceRequest message.
 *
 * @param {Buffer|Uint8Array} buffer - Raw protobuf bytes from a trusted source.
 * @returns {Promise<object>} Decoded message as a plain object.
 */
async function decodeTraceRequest(buffer) {
  const root = await getRoot();
  const ExportTraceServiceRequest = root.lookupType(
    'opentelemetry.consumer.ExportTraceServiceRequest'
  );

  const message = ExportTraceServiceRequest.decode(buffer);
  return ExportTraceServiceRequest.toObject(message, {
    longs: String,
    enums: String,
    bytes: String,
    defaults: false,
  });
}

/**
 * Encode an ExportTraceServiceRequest message to binary protobuf format.
 *
 * @param {object} payload - Plain object conforming to ExportTraceServiceRequest.
 * @returns {Promise<Buffer>} Encoded protobuf bytes.
 */
async function encodeTraceRequest(payload) {
  const root = await getRoot();
  const ExportTraceServiceRequest = root.lookupType(
    'opentelemetry.consumer.ExportTraceServiceRequest'
  );

  const errMsg = ExportTraceServiceRequest.verify(payload);
  if (errMsg) {
    throw new Error(`Invalid ExportTraceServiceRequest payload: ${errMsg}`);
  }

  const message = ExportTraceServiceRequest.create(payload);
  return Buffer.from(ExportTraceServiceRequest.encode(message).finish());
}

/**
 * Process a single raw protobuf buffer received from a trusted message queue.
 * Decodes the message and emits it to the provided handler.
 *
 * @param {Buffer|Uint8Array} buffer - Raw bytes from message queue.
 * @param {function} handler - Async function that receives the decoded trace request.
 * @returns {Promise<void>}
 */
async function processMessage(buffer, handler) {
  if (!Buffer.isBuffer(buffer) && !(buffer instanceof Uint8Array)) {
    throw new TypeError('buffer must be a Buffer or Uint8Array');
  }
  if (typeof handler !== 'function') {
    throw new TypeError('handler must be a function');
  }

  const decoded = await decodeTraceRequest(buffer);
  await handler(decoded);
}

module.exports = { decodeTraceRequest, encodeTraceRequest, processMessage };

// Allow running directly for smoke-testing.
if (require.main === module) {
  (async () => {
    console.log('opentelemetry-consumer-1: protobuf definitions loaded from', PROTO_PATH);
    const root = await getRoot();
    const type = root.lookupType('opentelemetry.consumer.ExportTraceServiceRequest');
    console.log('ExportTraceServiceRequest type resolved:', type.fullName);
    console.log('Consumer ready.');
  })().catch((err) => {
    console.error('Failed to start consumer:', err);
    process.exitCode = 1;
  });
}
